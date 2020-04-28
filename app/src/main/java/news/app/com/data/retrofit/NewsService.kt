package news.app.com.data.retrofit

import news.app.com.BuildConfig
import news.app.com.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsService{
//    @GET("nl6jh")
    @GET("svc/mostpopular/v2/viewed/1.json?api-key=${BuildConfig.NewsApiKey}")
    suspend fun getNews(): NewsResponse
}