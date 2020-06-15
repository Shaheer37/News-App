package news.app.com.ui.injection.modules

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import news.app.com.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import news.app.com.ui.App
import news.app.com.ui.NewsMapper
import news.app.com.ui.SourceMapper
import news.app.com.ui.UiThread
import news.app.com.ui.injection.components.AppComponent
import news.app.com.ui.injection.subcomponents.NewsFragmentSubcomponent
import news.app.com.ui.news.MainActivity
import news.app.com.ui.news.newsdetails.NewsDetailFragment
import news.app.com.ui.news.viewnews.NewsFragment
import news.app.com.ui.utils.getUTCDateTimeFormatter
import news.app.com.ui.utils.getCurrentLocale
import java.text.SimpleDateFormat
import javax.inject.Named

@ExperimentalPagingApi
@Module(subcomponents = [NewsFragmentSubcomponent::class])
interface UiModule {

    @Module
    companion object{
//        @Provides
//        @Named(AppComponent.ARTICLE_DATE_FORMATTER)
//        fun providesArticlePublishedDateFormatter(app: App): SimpleDateFormat{
//            return app.getCurrentLocale().getUTCDateTimeFormatter()
//        }

        @Provides
        @JvmStatic
        fun providesNewsMapper(@Named(AppComponent.CONTEXT) context: Context): NewsMapper{
            return NewsMapper(context.getCurrentLocale().getUTCDateTimeFormatter(), SourceMapper())
        }
    }

    @Binds
    @Named(AppComponent.CONTEXT)
    fun bindsContext(app: App): Context

    @ExperimentalPagingApi
    @Binds
    @IntoMap
    @ClassKey(NewsFragment::class)
    fun bindYourAndroidInjectorFactory(factory: NewsFragmentSubcomponent.Factory): AndroidInjector.Factory<*>

    @ContributesAndroidInjector
    fun contributesNewsDetailFragment(): NewsDetailFragment

    @ContributesAndroidInjector
    fun contributesMainActivity(): MainActivity

    @Binds
    fun bindsPostExecutionThread(postExecutionThread: UiThread): PostExecutionThread
}