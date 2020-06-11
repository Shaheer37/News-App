package news.app.com.ui.viewnews

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import news.app.com.data.NewsDbMapper
import news.app.com.data.NewsRepositoryImpl
import news.app.com.data.SourceDbMapper
import news.app.com.domain.GetNewsUsecase
import news.app.com.domain.GetNewsUsecaseImpl
import news.app.com.domain.NewsRepository
import news.app.com.domain.models.DataResult
import news.app.com.domain.models.NewsModel
import news.app.com.test.factory.DataFactory
import news.app.com.ui.NewsMapper
import news.app.com.ui.SourceMapper
import news.app.com.ui.createMockDataSourceFactory
import news.app.com.ui.getOrAwaitValue
import news.app.com.ui.news.viewnews.NewsViewModel
import news.app.com.ui.test.factory.NewsDataFactory
import news.app.com.ui.models.Result
import news.app.com.ui.news.viewnews.NewsBoundaryCallback
import news.app.com.ui.utils.getUTCDateTimeFormatter
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class NewsViewModelTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    private val newsDbMapper = NewsDbMapper(SourceDbMapper())
    private val newsDataMapper = news.app.com.data.NewsMapper(news.app.com.data.SourceMapper())

    private val getNewsUsecase = mock(GetNewsUsecase::class.java)

    private val newsBoundaryCallback = mock(NewsBoundaryCallback::class.java)
    private val newsMapper = NewsMapper(DataFactory.getLocale().getUTCDateTimeFormatter(), SourceMapper())

    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun before(){
        newsViewModel = NewsViewModel(newsMapper, getNewsUsecase, newsBoundaryCallback)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getNews() = runBlockingTest{
        val newsModels = mutableListOf(NewsDataFactory.makeNewsModel())
        for(i in 0 until 37){
            newsModels += NewsDataFactory.makeNewsModel()
        }
        stubGetNewsUsecase(createMockDataSourceFactory(newsModels))

        val news = newsModels.map { newsMapper.mapToView(it) }

        newsViewModel.getNews()

        val result = newsViewModel.news.getOrAwaitValue()
        println(result.toList().toString())
        Assert.assertEquals(news, result.toList())

        newsViewModel.setErrorLayoutVisibility(isVisible = false)
        val isErrorLayoutVisible = newsViewModel.isErrorLayoutVisible.getOrAwaitValue()
        Assert.assertEquals(false, isErrorLayoutVisible.getContentIfNotHandled())
    }

    fun stubGetNewsUsecase(newsDataSourceFactory: DataSource.Factory<Int, NewsModel>) = runBlocking{
        `when`(getNewsUsecase.invoke()).thenReturn(newsDataSourceFactory)
    }
}