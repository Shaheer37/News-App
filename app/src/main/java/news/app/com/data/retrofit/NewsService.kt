package news.app.com.data.retrofit

import news.app.com.BuildConfig
import news.app.com.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService{
//    @GET("nl6jh")
    @GET("top-headlines")
    @Headers("X-Api-Key: ${BuildConfig.NewsApiKey}")
    suspend fun getNews(@Query("country") countryCode: String="US"): NewsResponse
}