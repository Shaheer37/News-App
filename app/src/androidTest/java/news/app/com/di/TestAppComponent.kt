package news.app.com.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import news.app.com.ui.App
import news.app.com.ui.injection.modules.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        UiModule::class,
        DomainModule::class,
        DataModule::class,
        PresentationModule::class
    ]
)
interface TestAppComponent {
    companion object{
        public const val ARTICLE_DATE_FORMATTER = "articleDateFormatter"
        public const val CONTEXT = "appContext"
    }

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app: App): TestAppComponent
    }

    fun inject(app: App)
}