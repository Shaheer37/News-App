package news.app.com

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.test.runner.AndroidJUnitRunner

@ExperimentalPagingApi
class AndroidTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}