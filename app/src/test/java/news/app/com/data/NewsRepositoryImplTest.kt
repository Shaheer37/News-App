package news.app.com.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import news.app.com.data.retrofit.NewsService
import news.app.com.data.test.factory.NewsDataFactory
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException
import org.junit.rules.ExpectedException



@RunWith(JUnit4::class)
class NewsRepositoryImplTest {

    private val newsMapper = NewsMapper(SourceMapper())
    private val newsService = mock(NewsService::class.java)

    private val newsRepositoryImpl = NewsRepositoryImpl(newsService, newsMapper)

    val exception = ExpectedException.none()
    @Rule get

    @Test
    @ExperimentalCoroutinesApi
    fun getNewsTest() = runBlockingTest{
        val newsEntities = listOf(
            NewsDataFactory.makeNewsEntity(),
            NewsDataFactory.makeNewsEntity(),
            NewsDataFactory.makeNewsEntity()
        )

        val expectedNewsModels = newsEntities.map { newsMapper.mapToDomain(it) }
        stubNewsServiceGetNews(newsEntities)

        val actualNewsModels = newsRepositoryImpl.getNews()

        assertEquals(expectedNewsModels, actualNewsModels)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getNewsFailureTest() = runBlockingTest{
        stubNewsServiceGetNews_failedScenario()

        exception.expect(IOException::class.java)
        exception.expectMessage("Error! Unsuccessful response from server.")
        newsRepositoryImpl.getNews()
    }

    suspend fun stubNewsServiceGetNews_failedScenario() {
        `when`(newsService.getNews()).thenReturn(NewsDataFactory.makeFailedNewsResponse())
    }

    suspend fun stubNewsServiceGetNews(newsList: List<NewsEntity>) {
        `when`(newsService.getNews()).thenReturn(NewsDataFactory.makeNewsResponse(newsList.size, newsList))
    }


}