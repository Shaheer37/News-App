package news.app.com.domain

import androidx.paging.DataSource
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import news.app.com.domain.executor.PostExecutionThread
import kotlinx.coroutines.withContext
import news.app.com.domain.models.NewsModel
import javax.inject.Inject

class GetNewsUsecaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postExecutionThread: PostExecutionThread
): GetNewsUsecase {
    override fun invoke(): Flow<PagingData<NewsModel>>{
        return newsRepository.getNews()
    }
}

interface GetNewsUsecase {
    operator fun invoke(): Flow<PagingData<NewsModel>>
}
