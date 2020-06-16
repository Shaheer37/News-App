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
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

private const val INITIAL_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
        private val newsService: NewsService,
        private val newsDatabase: NewsDatabase,
        private val newsDbMapper: NewsDbMapper
): RemoteMediator<Int, News>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, News>): MediatorResult {
        Timber.d("load(loadType: $loadType, state: $state")
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if(remoteKeys?.nextKey == null){
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                remoteKeys.prevKey?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
        Timber.d("page: $page")

        return try{
            val response = newsService.getNews(page = page)
            if(response.status == NewsService.RESPONSE_STATUS_OK){
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

                    newsDatabase.remoteKeysDao().insertAll(keys)
                    newsDatabase.newsDao().insertAllNews(newsResponse)
                }
                MediatorResult.Success(endOfPaginationReached)
            }else{
                MediatorResult.Error(IOException("Error! Unsuccessful response from server."))
            }
        }catch (exception: NoConnectivityException) {
            exception.printStackTrace()
            return /*if(loadType == LoadType.REFRESH && dbHasNews()){
                MediatorResult.Success(false)
            }else */MediatorResult.Error(exception)
        }catch (exception: IOException) {
            exception.printStackTrace()
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            exception.printStackTrace()
            MediatorResult.Error(exception)
        }
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