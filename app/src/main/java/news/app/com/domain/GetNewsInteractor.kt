package news.app.com.domain

import androidx.paging.DataSource
import news.app.com.domain.executor.PostExecutionThread
import kotlinx.coroutines.withContext
import news.app.com.domain.models.NewsModel
import javax.inject.Inject

class GetNewsUsecaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
): GetNewsUsecase {
    override suspend fun invoke(): DataSource.Factory<Int,NewsModel> = withContext(postExecutionThread.dispatcher){
        newsRepository.getNews()
    }
}

interface GetNewsUsecase {
    suspend operator fun invoke(): DataSource.Factory<Int,NewsModel>
}/*interface GetNewsUsecase<out T: Any> {
    suspend operator fun invoke(): T
}*/


class GetNewsFromApiUsecaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
): GetNewsFromApiUsecase {
    override suspend fun invoke(page: Int): Boolean = withContext(postExecutionThread.dispatcher){
        newsRepository.updateNews(page)
    }

}
interface GetNewsFromApiUsecase {
    suspend operator fun invoke(page: Int): Boolean
}
