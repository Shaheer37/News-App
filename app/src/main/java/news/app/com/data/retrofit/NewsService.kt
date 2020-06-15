package news.app.com.data.retrofit

import news.app.com.BuildConfig
import news.app.com.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService{
    companion object {
        const val RESPONSE_PAGE_SIZE = 10
        const val RESPONSE_STATUS_OK = "ok"
    }
//    @GET("nl6jh")
    @GET("top-headlines")
    @Headers("X-Api-Key: ${BuildConfig.NewsApiKey}")
    suspend fun getNews(
        @Query("country") countryCode: String="US",
        @Query("pageSize") pageSize: Int = RESPONSE_PAGE_SIZE,
        @Query("page") page: Int
    ): NewsResponse
}