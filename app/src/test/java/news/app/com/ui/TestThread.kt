package news.app.com.ui

import news.app.com.domain.executor.PostExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestThread: PostExecutionThread {
    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}