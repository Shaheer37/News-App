package news.app.com.ui

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import news.app.com.ui.injection.components.AppComponent
import news.app.com.ui.injection.components.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class App: Application(), HasAndroidInjector {
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.factory().create(this).inject(this)

        Fresco.initialize(this)
        Timber.plant(Timber.DebugTree())
    }
}