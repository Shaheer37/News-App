package news.app.com.ui.injection.subcomponents

import androidx.paging.ExperimentalPagingApi
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.AndroidInjector
import news.app.com.ui.injection.modules.NewsFragmentModule
import news.app.com.ui.news.viewnews.NewsFragment

@ExperimentalPagingApi
@Subcomponent(modules = [NewsFragmentModule::class])
interface NewsFragmentSubcomponent: AndroidInjector<NewsFragment>{
    @Subcomponent.Factory
    interface Factory: AndroidInjector.Factory<NewsFragment>
}