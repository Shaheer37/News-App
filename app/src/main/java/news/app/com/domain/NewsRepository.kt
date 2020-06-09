package news.app.com.domain

import androidx.paging.DataSource
import androidx.paging.PagedList
import news.app.com.domain.models.NewsModel

interface NewsRepository {
    suspend fun getNews(): DataSource.Factory<Int, NewsModel>
    suspend fun updateNews(page: Int): Boolean
}