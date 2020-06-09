package news.app.com.ui.news.viewnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.*
import news.app.com.domain.GetNewsFromApiUsecase
import news.app.com.ui.models.News
import timber.log.Timber
import javax.inject.Inject

class NewsBoundaryCallback @Inject constructor(
        private val getNewsFromApi: GetNewsFromApiUsecase
):PagedList.BoundaryCallback<News>() {
    companion object{
        const val NEWS_PER_PAGE = 20
    }

    private var canFetchMore = true
    private var hasFistLoadedRan = false

    private var lastRequestedPage = 2

    private val _isRequestInProgress = MutableLiveData(false)
    val isRequestInProgress: LiveData<Boolean>
        get() = _isRequestInProgress

    private val _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onItemAtFrontLoaded(itemAtFront: News) {
        if(!hasFistLoadedRan){
            Timber.d("onItemAtFrontLoaded(itemAtFront: $itemAtFront)")
            hasFistLoadedRan = true
            lastRequestedPage = 1
            getNews()
        }
    }

    override fun onZeroItemsLoaded() {
        Timber.d("onZeroItemsLoaded()")
        lastRequestedPage = 1
        canFetchMore = true
        getNews()
    }

    override fun onItemAtEndLoaded(itemAtEnd: News) {
        Timber.d("onItemAtEndLoaded(itemAtEnd: $itemAtEnd)")
        getNews()
    }

    private fun getNews() {

        if(_isRequestInProgress.value!!) return
        if(!canFetchMore) return

        GlobalScope.launch(Dispatchers.IO) {
            setRequestProgressStatus(true)
            try{
                canFetchMore = getNewsFromApi(lastRequestedPage)
                lastRequestedPage++
                setRequestProgressStatus(false)
            }catch (e: Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main) {_networkErrors.value = e.localizedMessage ?: "Error! Something went wrong."}
                setRequestProgressStatus(false)
            }
        }
    }

    suspend fun setRequestProgressStatus(status: Boolean) = withContext(Dispatchers.Main){
        _isRequestInProgress.value = status
    }
}