package news.app.com.ui

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import news.app.com.ui.injection.components.AppComponent
import news.app.com.ui.injection.components.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

@ExperimentalPagingApi
open class App: Application(), HasAndroidInjector {
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }


    open fun initDagger(){
        DaggerAppComponent.factory().create(this).inject(this)
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()

        Fresco.initialize(this)
        Timber.plant(Timber.DebugTree())
    }
}