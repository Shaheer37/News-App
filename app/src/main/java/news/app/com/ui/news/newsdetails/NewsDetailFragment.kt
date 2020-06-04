package news.app.com.ui.news.newsdetails


import android.content.ContentResolver
import android.content.Intent
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequestBuilder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_news_detail.*

import news.app.com.R
import news.app.com.ui.EventObserver
import news.app.com.ui.injection.ViewModelFactory
import news.app.com.ui.models.News
import news.app.com.ui.utils.EspressoIdlingResource
import timber.log.Timber
import javax.inject.Inject

class NewsDetailFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(news: News) =
            NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("news", news)
                }
            }
    }

    @Inject lateinit var viewModel: NewsDetailViewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory

    val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(NewsDetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.newsTitle.observe(viewLifecycleOwner, Observer {
            news_title.text = it
        })

        viewModel.newsSummary.observe(viewLifecycleOwner, Observer {
            news_summary.text = it
        })

        viewModel.newsImage.observe(viewLifecycleOwner, Observer {
            news_image.setImageURI(it)
        })

        viewModel.newsImageDescription.observe(viewLifecycleOwner, Observer {
            news_image.contentDescription = it
        })

        viewModel.onOpenNewsArticle.observe(viewLifecycleOwner, EventObserver{
            openNewsArticlePage(it)
        })

        news_article_link_btn.setOnClickListener { viewModel.onArticleLinkClicked() }

        args.news?.let { viewModel.setNewsData(it) }
    }

    private fun openNewsArticlePage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        context?.let{
            if (intent.resolveActivity(it.packageManager) != null) startActivity(intent)
        }
    }
}
