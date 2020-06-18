package news.app.com.ui.news.viewnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.*
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import news.app.com.R
import news.app.com.ui.EventObserver
import news.app.com.ui.news.newsdetails.NewsDetailFragment
import news.app.com.ui.injection.ViewModelFactory
import news.app.com.ui.models.News
import news.app.com.ui.news.MainViewModel
import news.app.com.ui.utils.*
import timber.log.Timber
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
open class NewsFragment: Fragment(), OnNewsClickedEventListener{

    @Inject lateinit var viewModel: NewsViewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var adapter: NewsAdapter

    private val isTablet: Boolean by lazy { resources.getBoolean(R.bool.is_tablet) }

    private var newsJob: Job? = null

    private var isLoadingRefresh = false

    @VisibleForTesting
    open fun injectDependencies(){
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(NewsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        news_list.adapter = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        news_swipe_refresh.setOnRefreshListener {
            Timber.d("setOnRefreshListener()")
            refreshNews()
        }

        retry_btn.setOnClickListener {
            Timber.d("setOnRetryClickListener()")
            refreshNews()
        }

        viewModel.isErrorLayoutVisible.observe(viewLifecycleOwner, EventObserver{isVisible->
            if(isVisible){
                news_error_msg.visibility = View.VISIBLE
                retry_btn.visibility = View.VISIBLE
            }else{
                news_error_msg.visibility = View.INVISIBLE
                retry_btn.visibility = View.INVISIBLE
            }
        })

        getNews()
    }

    private fun initAdapter(){
        news_list.adapter = adapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { adapter.retry() },
                footer = NewsLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            Timber.d("loadStateChange: "+loadState)

            if (loadState.refresh !is LoadState.NotLoading) {

                news_swipe_refresh.isRefreshing = loadState.refresh is LoadState.Loading

                if(loadState.refresh is LoadState.Error){
                    Timber.d("loadState.refresh: "+loadState.refresh)
                    if(adapter.itemCount<=0) viewModel.setErrorLayoutVisibility(true)
                    else context?.toast(getString(R.string.get_news_fail))
                    EspressoIdlingResource.decrement()
                }

            } else {

                news_swipe_refresh.isRefreshing = false
                viewModel.setErrorLayoutVisibility(false)

                val errorState = when {
                    loadState.append is LoadState.Error -> {
                        loadState.append as LoadState.Error
                    }
                    loadState.prepend is LoadState.Error -> {
                        loadState.prepend as LoadState.Error
                    }
                    else -> {
                        null
                    }
                }
                errorState?.let {
                    Timber.d(it.error)
                    context?.toast(getString(R.string.get_news_fail))
                }
            }
        }

        adapter.addDataRefreshListener {
            Timber.d("DataRefreshListener")
            EspressoIdlingResource.decrement()
            showFirstItemIfTablet(adapter.getFirstItem())
            viewModel.setErrorLayoutVisibility(isVisible = false)
        }
    }

    fun getNews(){
        newsJob?.cancel()
        newsJob = lifecycleScope.launch {
            viewModel.getNews()
            .collectLatest {
                Timber.d("collectLatest")
                EspressoIdlingResource.increment()
                adapter.submitData(it)
            }
        }
    }

    private fun refreshNews(){
        Timber.d("refreshNews()")
        adapter.refresh()
    }


    private fun showFirstItemIfTablet(news: News?){
        if(isTablet && news != null && !childFragmentManager.hasFragments()){
            onNewsClicked(news)
        }
    }

    override fun onNewsClicked(news: News) {
        if(isTablet) replaceFragment(NewsDetailFragment.newInstance(news), R.id.fragment_container)
        else findNavController().navigate(NewsFragmentDirections.actionNewsToNewsDetail(news))
    }
}