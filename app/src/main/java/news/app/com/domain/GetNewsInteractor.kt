package news.app.com.domain

import news.app.com.domain.executor.PostExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import news.app.com.domain.models.NewsModel
import news.app.com.domain.models.NetworkResult
import javax.inject.Inject

class GetNewsUsecaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
): GetNewsUsecase {
    override suspend fun invoke(): NetworkResult<List<NewsModel>> = withContext(postExecutionThread.dispatcher){
        try {
            NetworkResult.Success(newsRepository.getNews())
        }catch (ex: Exception){
            ex.printStackTrace()
            NetworkResult.Error(ex)
        }
    }
}

interface GetNewsUsecase {
    suspend operator fun invoke(): NetworkResult<List<NewsModel>>
}