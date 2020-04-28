package news.app.com.ui

import news.app.com.domain.executor.PostExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UiThread @Inject constructor(): PostExecutionThread {
    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO
}