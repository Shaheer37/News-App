package news.app.com

import news.app.com.di.DaggerTestAppComponent
import news.app.com.ui.App

class TestApp: App() {
    override fun initDagger() {
        DaggerTestAppComponent.factory().create(this).inject(this)
    }
}