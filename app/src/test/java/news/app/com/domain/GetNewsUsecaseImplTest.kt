package news.app.com.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import news.app.com.domain.models.DataResult
import news.app.com.domain.models.NewsModel
import news.app.com.domain.test.factory.NewsModelFactory
import news.app.com.ui.TestThread
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class GetNewsUsecaseImplTest{

    private val newsRepository = mock(NewsRepository::class.java)

    private val newsUsecaseImpl = GetNewsUsecaseImpl(newsRepository, TestThread())

    @Test
    @ExperimentalCoroutinesApi
    fun testNewsUsecase_successfulcase() = runBlockingTest{
        newsUsecaseImpl()

        verify(newsRepository).getNews()
    }
}
