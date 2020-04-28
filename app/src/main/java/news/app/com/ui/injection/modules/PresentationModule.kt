package news.app.com.ui.injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import news.app.com.ui.injection.ViewModelFactory
import news.app.com.ui.news.newsdetails.NewsDetailViewModel
import news.app.com.ui.news.viewnews.NewsViewModel
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
interface PresentationModule{
    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    fun bindsNewsViewModel(viewModel: NewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailViewModel::class)
    fun bindsNewsDetailViewModel(viewModel: NewsDetailViewModel): ViewModel

    @Binds
    fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)