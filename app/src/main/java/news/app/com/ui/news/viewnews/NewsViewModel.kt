package news.app.com.ui.news.viewnews

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import news.app.com.domain.GetNewsUsecase
import news.app.com.domain.models.NewsModel
import news.app.com.ui.Event
import news.app.com.ui.NewsMapper
import news.app.com.ui.models.News
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsMapper: NewsMapper,
    private val getNewsUsecase: GetNewsUsecase,
    private val newsBoundaryCallback: NewsBoundaryCallback
): ViewModel(){
    companion object{
        const val DATABASE_PAGE_SIZE = 20
    }

    private var newsDataSourceFactory = MutableLiveData<DataSource.Factory<Int,News>>()
    val news: LiveData<PagedList<News>> = Transformations.switchMap(newsDataSourceFactory){
        LivePagedListBuilder(it, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(newsBoundaryCallback)
                .build()
    }

    val error: LiveData<Event<String>> = Transformations.map(newsBoundaryCallback.networkErrors){
        Event(it)
    }

    val loadingNews: LiveData<Event<Boolean>> = Transformations.map(newsBoundaryCallback.isRequestInProgress){
        Event(it)
    }


    private var _isErrorLayoutVisible = MutableLiveData<Event<Boolean>>()
    val isErrorLayoutVisible: LiveData<Event<Boolean>> = _isErrorLayoutVisible

    fun getNews() = viewModelScope.launch(Dispatchers.Main){

        getNewsUsecase().run{
            newsDataSourceFactory.value = map{
                newsMapper.mapToView(it)
            }
        }
    }

    fun setErrorLayoutVisibility(isVisible: Boolean){
        _isErrorLayoutVisible.value = Event(isVisible)
    }
}

data class NewsResult(
    val data: LiveData<PagedList<News>>,
    val networkErrors: LiveData<String>
)