package news.app.com.ui.news.viewnews

import androidx.lifecycle.*
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import news.app.com.domain.GetNewsUsecase
import news.app.com.ui.Event
import news.app.com.ui.NewsMapper
import news.app.com.ui.models.News
import timber.log.Timber
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsMapper: NewsMapper,
    private val getNewsUsecase: GetNewsUsecase
): ViewModel(){
    companion object{
        const val DATABASE_PAGE_SIZE = 10
    }

    private var _isErrorLayoutVisible = MutableLiveData<Event<Boolean>>()
    val isErrorLayoutVisible: LiveData<Event<Boolean>> = _isErrorLayoutVisible

    private var currentNews: Flow<PagingData<News>>? = null

    fun getNews(): Flow<PagingData<News>> {
        Timber.d("getNews()")

        currentNews?.let { return it }

        val news = getNewsUsecase()
                .map { it.map { newsModel -> newsMapper.mapToView(newsModel) } }
                .cachedIn(viewModelScope)
        currentNews = news
        return news
    }

    fun setErrorLayoutVisibility(isVisible: Boolean){
        _isErrorLayoutVisible.value = Event(isVisible)
    }
}