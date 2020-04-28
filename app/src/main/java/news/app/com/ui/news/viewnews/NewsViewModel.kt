package news.app.com.ui.news.viewnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import news.app.com.domain.GetNewsUsecase
import news.app.com.domain.models.NetworkResult
import news.app.com.ui.Event
import news.app.com.ui.NewsMapper
import news.app.com.ui.models.News
import news.app.com.ui.models.Result
import news.app.com.ui.models.succeeded
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsMapper: NewsMapper,
    private val getNewsUsecase: GetNewsUsecase
): ViewModel(){

    private var _news = MutableLiveData<Result<List<News>>>()
    val news: LiveData<Result<List<News>>> = _news


    private var _isErrorLayoutVisible = MutableLiveData<Event<Boolean>>()
    val isErrorLayoutVisible: LiveData<Event<Boolean>> = _isErrorLayoutVisible

    fun getNews() = viewModelScope.launch(Dispatchers.Main){
        _news.value = Result.Loading

        val news = getNewsUsecase()

        when(news){
            is NetworkResult.Success -> {
                _news.value = Result.Success(news.data.map{
                    newsMapper.mapToView(it)
                })
            }
            is NetworkResult.Error -> {
                _news.value = Result.Error(news.exception)
            }
        }
    }

    fun setErrorLayoutVisibility(isVisible: Boolean){
        _isErrorLayoutVisible.value = Event(isVisible)
    }
}