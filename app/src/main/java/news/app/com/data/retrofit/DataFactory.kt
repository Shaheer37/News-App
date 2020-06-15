package news.app.com.data.retrofit

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import news.app.com.data.NewsEntity
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataFactory{

    private const val BASE_URL = "https://newsapi.org/v2/"

    fun makeService(baseUrl: HttpUrl = BASE_URL.toHttpUrl(), context: Context, showLogs: Boolean = true): NewsService {
        val okHttpClient = makeOkHttpClient(
                makeNetworkConnectivityInterceptor(context),
                makeLoggingInterceptor(showLogs)
        )
        return makeService(baseUrl, okHttpClient)
    }

    private fun makeService(baseUrl: HttpUrl, okHttpClient: OkHttpClient): NewsService {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()
        return retrofit.create(NewsService::class.java)
    }

    private fun makeOkHttpClient(
            networkConnectivityInterceptor: NetworkConnectionInterceptor,
            httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkConnectivityInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun getGson(): Gson{
        return GsonBuilder()
            .create()
    }

    private fun makeNetworkConnectivityInterceptor(context: Context): NetworkConnectionInterceptor{
        return NetworkConnectionInterceptor(context)
    }


    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.level = if(isDebug){
            HttpLoggingInterceptor.Level.BODY
        } else{
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}