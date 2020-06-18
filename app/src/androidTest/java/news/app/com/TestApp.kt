package news.app.com

import androidx.paging.ExperimentalPagingApi
import news.app.com.di.DaggerTestAppComponent
import news.app.com.ui.App

@ExperimentalPagingApi
class TestApp: App() {
    override fun initDagger() {
        DaggerTestAppComponent.factory().create(this).inject(this)
    }
}