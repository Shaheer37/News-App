package news.app.com.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import news.app.com.data.persistence.News
import news.app.com.data.persistence.NewsDatabase
import news.app.com.data.persistence.RemoteKeys
import news.app.com.data.retrofit.NewsService
import news.app.com.data.retrofit.NoConnectivityException
import news.app.com.ui.injection.modules.DataModule
import news.app.com.ui.utils.EspressoIdlingResource
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.io.InvalidObjectException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

private const val INITIAL_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
        private val newsService: NewsService,
        private val newsDatabase: NewsDatabase,
        private val newsDbMapper: NewsDbMapper,
        private val sessionManager: SessionManager,
        @Named(DataModule.DATE_FORMATTER) private val dateFormatter: SimpleDateFormat
): RemoteMediator<Int, News>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, News>): MediatorResult {
        Timber.d("load(loadType: $loadType, state: $state")

        EspressoIdlingResource.increment()

        return try{
            getMediatorResult(loadType, state).also { EspressoIdlingResource.decrement() }
        }catch (e: Exception){
            e.printStackTrace()
            EspressoIdlingResource.decrement()
            throw e
        }

    }

    private suspend fun getMediatorResult(loadType: LoadType, state: PagingState<Int, News>):MediatorResult{
        val page = try{
            val result = getPageOrReturn(loadType, state)
            result.second?.let { return it }
            result.first
        }catch (e: InvalidObjectException){
            throw e
        }

        return try{

            showDbData(page)?.let{
                return it
            }

            val response = newsService.getNews(page = page)
            if(response.status == NewsService.RESPONSE_STATUS_OK){

                sessionManager.totalNewsInResponse = response.totalResults

                val newsResponse = response.articles.map {
                    newsDbMapper.map(it)
                }
                val endOfPaginationReached = newsResponse.isEmpty()
                newsDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        newsDatabase.remoteKeysDao().clearRemoteKeys()
                        newsDatabase.newsDao().deleteAllNews()
                    }

                    val prevKey = if(page == INITIAL_PAGE) null else page-1
                    val nextKey = if(endOfPaginationReached) null else page+1

                    val keys = newsResponse.map{
                        RemoteKeys(it.title, prevKey, nextKey)
                    }

                    sessionManager.newsCacheDate = dateFormatter.format(Date())
                    newsDatabase.remoteKeysDao().insertAll(keys)
                    newsDatabase.newsDao().insertAllNews(newsResponse)
                }
                MediatorResult.Success(endOfPaginationReached)
            }else{
                MediatorResult.Error(IOException("Error! Unsuccessful response from server."))
            }
        }catch (exception: NoConnectivityException) {
            return MediatorResult.Error(exception)
        }catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getPageOrReturn(loadType: LoadType, state: PagingState<Int, News>): Pair<Int, MediatorResult?>{
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: throw InvalidObjectException("Remote key should not be null for $loadType")

                remoteKeys.nextKey?: return Pair(0, MediatorResult.Success(endOfPaginationReached = true))

                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                remoteKeys.prevKey?: return Pair(0, MediatorResult.Success(endOfPaginationReached = true))
                remoteKeys.prevKey
            }
        }

        return Pair(page, null)
    }

    private suspend fun showDbData(page: Int): MediatorResult?{
        val cacheDate = sessionManager.newsCacheDate
        val newsCount = withContext(Dispatchers.IO) {newsDatabase.newsDao().getNewsCount()}
        val currentDate = dateFormatter.format(Date())
        val totalNewsInResponse = sessionManager.totalNewsInResponse
        return if(newsCount>0 && cacheDate != null
                && currentDate == cacheDate){
            val newsInreferenceToPage = newsCount/(NewsService.RESPONSE_PAGE_SIZE*page)
            return when{
                (newsInreferenceToPage>0)->{
                    MediatorResult.Success(false)
                }
                (newsInreferenceToPage==0 && newsCount==totalNewsInResponse)->{
                    MediatorResult.Success(true)
                }
                else -> null
            }

        } else null
    }

    private suspend fun dbHasNews(): Boolean {
        return withContext(Dispatchers.IO){
            val newsCount = newsDatabase.newsDao().getNewsCount()
            newsCount>0
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, News>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { news ->
                    newsDatabase.remoteKeysDao().remoteKeysNewsId(news.title)
                }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, News>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { news ->
                    newsDatabase.remoteKeysDao().remoteKeysNewsId(news.title)
                }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
            state: PagingState<Int, News>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { repoId ->
                newsDatabase.remoteKeysDao().remoteKeysNewsId(repoId)
            }
        }
    }
}