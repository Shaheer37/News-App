package news.app.com.ui.news.newsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import news.app.com.ui.Event
import news.app.com.ui.models.News
import javax.inject.Inject

class NewsDetailViewModel @Inject constructor(): ViewModel() {

    private lateinit var newsArticleLink: String

    private var _newsTitle = MutableLiveData<String>()
    val newsTitle: LiveData<String> = _newsTitle

    private var _newsImage = MutableLiveData<String>()
    val newsImage: LiveData<String> = _newsImage

    private var _newsSummary = MutableLiveData<String>()
    val newsSummary: LiveData<String> = _newsSummary

    private var _newsImageDescription = MutableLiveData<String>()
    val newsImageDescription: LiveData<String> = _newsImageDescription

    private var _onOpenNewsArticle = MutableLiveData<Event<String>>()
    val onOpenNewsArticle: LiveData<Event<String>> = _onOpenNewsArticle

    fun setNewsData(news: News){
        _newsTitle.value = news.title
        _newsSummary.value = news.summary
        newsArticleLink = news.articleUrl
        _newsImage.value = news.image
        _newsImageDescription.value = news.getImageDescription()
    }

    fun onArticleLinkClicked(){
        _onOpenNewsArticle.value = Event(newsArticleLink)
    }
}