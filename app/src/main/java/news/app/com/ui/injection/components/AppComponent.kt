package news.app.com.ui.injection.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import news.app.com.ui.App
import news.app.com.ui.injection.modules.*
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        UiModule::class,
        PresentationModule::class,
        DataModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    companion object{
        public const val ARTICLE_DATE_FORMATTER = "articleDateFormatter"
        public const val CONTEXT = "appContext"
    }

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app: App): AppComponent
    }

    fun inject(app: App)
}