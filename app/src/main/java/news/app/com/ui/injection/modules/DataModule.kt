package news.app.com.ui.injection.modules

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import news.app.com.BuildConfig
import news.app.com.data.retrofit.DataFactory
import news.app.com.data.NewsRepositoryImpl
import news.app.com.data.persistence.NewsDatabase
import news.app.com.data.retrofit.NewsService
import news.app.com.domain.NewsRepository
import news.app.com.ui.injection.components.AppComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@ExperimentalPagingApi
interface DataModule {
    @Module
    companion object{
        @Provides
        @JvmStatic
        @Singleton
        fun providesNewsService(@Named(AppComponent.CONTEXT) context: Context): NewsService {
            return DataFactory.makeService(context = context,showLogs = BuildConfig.DEBUG)
        }


        @Provides
        @JvmStatic
        @Singleton
        fun providesNewsDatabase(@Named(AppComponent.CONTEXT) context: Context): NewsDatabase {
            return NewsDatabase.getInstance(context)
        }
    }

    @Binds
    @Singleton
    fun bindsNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository
}