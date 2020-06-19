package news.app.com.data

import androidx.paging.DataSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import news.app.com.data.persistence.News
import news.app.com.data.persistence.NewsDatabase
import news.app.com.data.persistence.NewsLocalData
import news.app.com.data.retrofit.NewsService
import news.app.com.data.retrofit.NewsService.Companion.RESPONSE_STATUS_OK
import news.app.com.domain.models.NewsModel
import news.app.com.domain.NewsRepository
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsLocalData: NewsLocalData,
    private val newsMapper: NewsMapper,
    private val newsRemoteMediator: NewsRemoteMediator
): NewsRepository {

    override fun getNews(): Flow<PagingData<NewsModel>> = Pager(
            config = PagingConfig(pageSize = NewsService.RESPONSE_PAGE_SIZE),
            remoteMediator = newsRemoteMediator,
            pagingSourceFactory = {newsLocalData.getNews()}
    ).flow.map{pagingData-> pagingData.map { newsMapper.map(it) }}
}
