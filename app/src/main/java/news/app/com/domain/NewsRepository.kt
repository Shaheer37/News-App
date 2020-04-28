package news.app.com.domain

import news.app.com.domain.models.NewsModel

interface NewsRepository {
    suspend fun getNews(): List<NewsModel>
}