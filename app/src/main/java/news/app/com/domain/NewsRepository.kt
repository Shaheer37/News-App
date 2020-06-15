package news.app.com.domain

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import news.app.com.domain.models.NewsModel

interface NewsRepository {
    fun getNews(): Flow<PagingData<NewsModel>>
}