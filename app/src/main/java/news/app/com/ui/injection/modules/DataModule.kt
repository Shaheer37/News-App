package news.app.com.ui.injection.modules

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import news.app.com.BuildConfig
import news.app.com.data.retrofit.DataFactory
import news.app.com.data.NewsRepositoryImpl
import news.app.com.data.SessionManager
import news.app.com.data.persistence.NewsDatabase
import news.app.com.data.retrofit.NewsService
import news.app.com.domain.NewsRepository
import news.app.com.ui.injection.components.AppComponent
import news.app.com.ui.utils.getCurrentLocale
import news.app.com.ui.utils.getUTCDateFormatter
import java.text.SimpleDateFormat
import javax.inject.Named
import javax.inject.Singleton

@Module
@ExperimentalPagingApi
interface DataModule {
    @Module
    companion object {

        public const val DATE_FORMATTER = "dateFormatter"

        @Provides
        @JvmStatic
        @Singleton
        fun providesNewsService(@Named(AppComponent.CONTEXT) context: Context): NewsService {
            return DataFactory.makeService(context = context, showLogs = BuildConfig.DEBUG)
        }


        @Provides
        @JvmStatic
        @Singleton
        fun providesNewsDatabase(@Named(AppComponent.CONTEXT) context: Context): NewsDatabase {
            return NewsDatabase.getInstance(context)
        }

        @Provides
        @JvmStatic
        @Named(DATE_FORMATTER)
        fun providesDateFormatter(@Named(AppComponent.CONTEXT) context: Context): SimpleDateFormat {
            return context.getCurrentLocale().getUTCDateFormatter()
        }

        @Provides
        @JvmStatic
        @Singleton
        fun providesSessionManager(@Named(AppComponent.CONTEXT) context: Context): SessionManager {
            return SessionManager(context)
        }

    }

    @Binds
    @Singleton
    fun bindsNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository
}