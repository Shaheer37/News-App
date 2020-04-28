package news.app.com.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import news.app.com.domain.models.NetworkResult
import news.app.com.domain.models.NewsModel
import news.app.com.domain.test.factory.NewsModelFactory
import news.app.com.ui.TestThread
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
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

        val newsModel = NewsModelFactory.makeNewsModel()
        stubNewsRepostory(listOf(newsModel))

        val expectedResult = NetworkResult.Success(listOf(newsModel))
        val actualResult = newsUsecaseImpl()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testNewsUsecase_failedcase() = runBlockingTest{

        val exception = Exception()

        stubNewsRepostoryThrows(exception)

        assertEquals(NetworkResult.Error(exception), newsUsecaseImpl())
    }

    fun stubNewsRepostory(returnData: List<NewsModel>) = runBlocking{
        `when`(newsRepository.getNews()).thenReturn(returnData)
    }

    fun stubNewsRepostoryThrows(exception: java.lang.Exception) = runBlocking{
        doAnswer{throw exception}.`when`(newsRepository).getNews()
    }
}
