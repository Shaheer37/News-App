package news.app.com.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface PostExecutionThread {
    val dispatcher: CoroutineDispatcher
}