package news.app.com.ui.injection.modules

import news.app.com.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import news.app.com.ui.UiThread
import news.app.com.ui.injection.subcomponents.NewsFragmentSubcomponent
import news.app.com.ui.news.newsdetails.NewsDetailFragment
import news.app.com.ui.news.viewnews.NewsFragment

@Module(subcomponents = [NewsFragmentSubcomponent::class])
interface UiModule {

    @Binds
    @IntoMap
    @ClassKey(NewsFragment::class)
    fun bindYourAndroidInjectorFactory(factory: NewsFragmentSubcomponent.Factory): AndroidInjector.Factory<*>

    @ContributesAndroidInjector
    fun contributesNewsDetailFragment(): NewsDetailFragment

    @Binds
    fun bindsPostExecutionThread(postExecutionThread: UiThread): PostExecutionThread
}