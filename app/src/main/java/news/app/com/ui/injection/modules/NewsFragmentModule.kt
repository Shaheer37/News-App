package news.app.com.ui.injection.modules

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import news.app.com.ui.NewsMapper
import news.app.com.ui.SourceMapper
import news.app.com.ui.injection.components.AppComponent
import news.app.com.ui.news.viewnews.NewsFragment
import news.app.com.ui.news.viewnews.OnNewsClickedEventListener
import news.app.com.ui.utils.getCurrentLocale
import news.app.com.ui.utils.getUTCDateTimeFormatter
import javax.inject.Named

@Module
interface NewsFragmentModule {

    @ExperimentalPagingApi
    @Binds
    fun bindsOnNewsClickedEventListener(onNewsClickedEventListener: NewsFragment): OnNewsClickedEventListener
}