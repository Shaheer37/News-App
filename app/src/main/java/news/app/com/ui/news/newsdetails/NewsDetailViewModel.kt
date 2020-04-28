package news.app.com.ui.news.newsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import news.app.com.ui.Event
import news.app.com.ui.models.Image
import news.app.com.ui.models.News
import javax.inject.Inject

class NewsDetailViewModel @Inject constructor(): ViewModel() {

    private lateinit var newsArticleLink: String

    private var _newsTitle = MutableLiveData<String>()
    val newsTitle: LiveData<String> = _newsTitle

    private var _newsImage = MutableLiveData<Image>()
    val newsImage: LiveData<Image> = _newsImage

    private var _newsSummary = MutableLiveData<String>()
    val newsSummary: LiveData<String> = _newsSummary

    private var _onOpenNewsArticle = MutableLiveData<Event<String>>()
    val onOpenNewsArticle: LiveData<Event<String>> = _onOpenNewsArticle

    fun setNewsData(news: News){
        _newsTitle.value = news.title
        _newsSummary.value = news.summary
        newsArticleLink = news.articleUrl
        if(news.articleImage != null) _newsImage.value = news.articleImage
    }

    fun onArticleLinkClicked(){
        _onOpenNewsArticle.value = Event(newsArticleLink)
    }
}