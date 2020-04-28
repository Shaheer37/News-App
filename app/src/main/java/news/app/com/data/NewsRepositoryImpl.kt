package news.app.com.data

import news.app.com.data.retrofit.NewsService
import news.app.com.domain.models.NewsModel
import news.app.com.domain.NewsRepository
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import kotlin.Exception

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
    private val newsMapper: NewsMapper
): NewsRepository {

    override suspend fun getNews(): List<NewsModel> = newsService.getNews().run {
        return if(status == "OK") results.map {
            newsMapper.mapToDomain(it)
        }
        else throw IOException("Error! Unsuccessful response from server.")
    }
}