package news.app.com.data

import androidx.paging.DataSource
import news.app.com.data.persistence.News
import news.app.com.data.persistence.NewsDatabase
import news.app.com.data.retrofit.NewsService
import news.app.com.domain.models.NewsModel
import news.app.com.domain.NewsRepository
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDb: NewsDatabase,
    private val newsService: NewsService,
    private val newsMapper: NewsMapper,
    private val newsDbMapper: NewsDbMapper
): NewsRepository {

    override suspend fun getNews(): DataSource.Factory<Int, NewsModel> {
        return newsDb.newsDao().getNews().map { newsMapper.map(it) }
    }

    override suspend fun updateNews(page: Int): Boolean {
        try{
            newsService.getNews(page = page).run {
                Timber.d(this.toString())
                if (status == "ok"){
                    if(page == 1) newsDb.newsDao().deleteNews()
                    val dbNews = articles.map { newsDbMapper.map(it) }
                    newsDb.newsDao().insertAllNews(dbNews)

                    return articles.size >= NewsService.RESPONSE_PAGE_SIZE
                }else{
                    throw IOException("Error! Unsuccessful response from server.")
                }
            }
        }catch (exception: Exception){
            exception.printStackTrace()
            throw exception
        }
    }

    /*    override suspend fun getNews(): List<NewsModel> = newsService.getNews().run {
            Timber.d(this.toString())
            return if(status == "ok") articles.map {
                newsMapper.map(it)
            }

            else throw IOException("Error! Unsuccessful response from server.")
        }*/
}
