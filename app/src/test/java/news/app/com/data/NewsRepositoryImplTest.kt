package news.app.com.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import news.app.com.data.persistence.News
import news.app.com.data.persistence.NewsDao
import news.app.com.data.persistence.NewsDatabase
import news.app.com.data.retrofit.NewsService
import news.app.com.data.test.factory.NewsDataFactory
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import org.junit.rules.ExpectedException
import org.mockito.Mockito.*


@RunWith(JUnit4::class)
class NewsRepositoryImplTest {

    private val newsMapper = NewsMapper(SourceMapper())
    private val newsDbMapper = NewsDbMapper(SourceDbMapper())
    private val newsService = mock(NewsService::class.java)
    private val newsDatabase = mock(NewsDatabase::class.java)
    private val newsDao = mock(NewsDao::class.java)

    private val newsRepositoryImpl = NewsRepositoryImpl(newsDatabase, newsService, newsMapper, newsDbMapper)

    val exception = ExpectedException.none()
    @Rule get

    @Test
    @ExperimentalCoroutinesApi
    fun updateNewsTest() = runBlockingTest{
        val newsEntities = listOf(
            NewsDataFactory.makeNewsEntity(),
            NewsDataFactory.makeNewsEntityNullSourceId(),
            NewsDataFactory.makeNewsEntityNoSource()
        )

        val expectedNews = newsEntities.map { newsDbMapper.map(it) }
        stubNewsServiceGetNews(newsEntities)
        stubNewsDao()
        stubNewsDbDelete()
        stubInsertNews(expectedNews)

        assertEquals(false, newsRepositoryImpl.updateNews(1))

        verify(newsService).getNews(page = 1)
        verify(newsDao).deleteAllNews()
        verify(newsDao).insertAllNews(expectedNews)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getNewsFailureTest() = runBlockingTest{
        stubNewsServiceGetNews_failedScenario()

        exception.expect(IOException::class.java)
        exception.expectMessage("Error! Unsuccessful response from server.")
        newsRepositoryImpl.updateNews(1)
    }

    suspend fun stubNewsServiceGetNews_failedScenario() {
        `when`(newsService.getNews(page = 1)).thenReturn(NewsDataFactory.makeFailedNewsResponse())
    }

    suspend fun stubNewsServiceGetNews(newsList: List<NewsEntity>) {
        `when`(newsService.getNews(page = 1)).thenReturn(NewsDataFactory.makeNewsResponse(newsList))
    }

    fun stubNewsDao(){
        `when`(newsDatabase.newsDao()).thenReturn(newsDao)
    }

    suspend fun stubNewsDbDelete() {
        `when`(newsDao.deleteAllNews()).thenReturn(0)
    }

    suspend fun stubInsertNews(news:List<News>) {
        doNothing().`when`(newsDao).insertAllNews(news)
    }


}