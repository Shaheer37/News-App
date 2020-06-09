package news.app.com.ui.news.viewnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_news.*
import news.app.com.R
import news.app.com.ui.EventObserver
import news.app.com.ui.news.newsdetails.NewsDetailFragment
import news.app.com.ui.injection.ViewModelFactory
import news.app.com.ui.models.News
import news.app.com.ui.models.Result
import news.app.com.ui.utils.EspressoIdlingResource
import news.app.com.ui.utils.hasFragments
import news.app.com.ui.utils.replaceFragment
import news.app.com.ui.utils.toast
import timber.log.Timber
import javax.inject.Inject

open class NewsFragment: Fragment(), OnNewsClickedEventListener{

    @Inject lateinit var viewModel: NewsViewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var adapter: NewsAdapter

    private val isTablet: Boolean by lazy { resources.getBoolean(R.bool.is_tablet) }

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

        news_list.adapter = adapter

        news_swipe_refresh.setOnRefreshListener {
            news_swipe_refresh.isRefreshing = true
            getNews()
        }

        retry_btn.setOnClickListener { getNews() }

        viewModel.loadingNews.observe(viewLifecycleOwner, EventObserver{news_swipe_refresh.isRefreshing = it})

        viewModel.news.observe(viewLifecycleOwner, Observer{
            news_swipe_refresh.isRefreshing = false
            submitlist(it)
            showFirstItemIfTable(it.snapshot())
            viewModel.setErrorLayoutVisibility(isVisible = false)
        })

        viewModel.error.observe(viewLifecycleOwner, EventObserver{
            news_swipe_refresh.isRefreshing = false
            if(adapter.itemCount<=0) viewModel.setErrorLayoutVisibility(isVisible = true)
            else context?.toast(getString(R.string.get_news_fail))
            EspressoIdlingResource.decrement()
        })

        viewModel.isErrorLayoutVisible.observe(viewLifecycleOwner, EventObserver{isVisible->
            if(isVisible){
                news_error_msg.visibility = View.VISIBLE
                retry_btn.visibility = View.VISIBLE
            }else{
                news_error_msg.visibility = View.INVISIBLE
                retry_btn.visibility = View.INVISIBLE
            }
        })

        if(adapter.itemCount<=0) getNews()
    }

    fun getNews(){
        EspressoIdlingResource.increment()
        viewModel.getNews()
    }

    private fun submitlist(newsList: PagedList<News>){
        adapter.submitList(newsList){
            EspressoIdlingResource.decrement()
        }
    }

    private fun showFirstItemIfTable(newsList: List<News>){
        if(isTablet && newsList.isNotEmpty() && !childFragmentManager.hasFragments()){
            onNewsClicked(newsList.first())
        }
    }

    override fun onNewsClicked(news: News) {
        if(isTablet) replaceFragment(NewsDetailFragment.newInstance(news), R.id.fragment_container)
        else findNavController().navigate(NewsFragmentDirections.actionNewsToNewsDetail(news))
    }
}