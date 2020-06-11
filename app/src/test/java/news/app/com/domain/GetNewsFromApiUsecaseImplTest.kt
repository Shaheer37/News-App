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
import java.io.IOException

@RunWith(JUnit4::class)
class GetNewsFromApiUsecaseImplTest{

    private val newsRepository = mock(NewsRepository::class.java)

    private val newsUsecaseImpl = GetNewsFromApiUsecaseImpl(newsRepository, TestThread())

    @Test
    @ExperimentalCoroutinesApi
    fun testNewsUsecase_successfulcase() = runBlockingTest{

        stubNewsRepostory()

        val actualResult = newsUsecaseImpl(1)

        assertEquals(true, actualResult)
    }

    @Test(expected = Exception::class)
    @ExperimentalCoroutinesApi
    fun testNewsUsecase_failedcase() = runBlockingTest{

        val exception = Exception()
        stubNewsRepostoryThrows(exception)

        assertEquals(exception, newsUsecaseImpl(1))
    }

    fun stubNewsRepostory() = runBlocking{
        `when`(newsRepository.updateNews(1)).thenReturn(true)
    }

    fun stubNewsRepostoryThrows(exception: java.lang.Exception) = runBlocking{
        doThrow(exception).`when`(newsRepository).updateNews(1)
    }
}
