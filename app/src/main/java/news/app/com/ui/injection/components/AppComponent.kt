package news.app.com.ui.injection.components

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
        PresentationModule::class,
        DataModule::class,
        DomainModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }

    fun inject(app: App)
}