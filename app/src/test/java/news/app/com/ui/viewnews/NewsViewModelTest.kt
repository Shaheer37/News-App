package news.app.com.ui.viewnews

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import news.app.com.domain.GetNewsUsecase
import news.app.com.domain.models.NetworkResult
import news.app.com.domain.models.NewsModel
import news.app.com.test.factory.DataFactory
import news.app.com.ui.NewsMapper
import news.app.com.ui.SourceMapper
import news.app.com.ui.getOrAwaitValue
import news.app.com.ui.news.viewnews.NewsViewModel
import news.app.com.ui.test.factory.NewsDataFactory
import news.app.com.ui.models.Result
import news.app.com.ui.utils.getUTCDateTimeFormatter
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config
import java.util.*

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class NewsViewModelTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    private val getNewsUsecase = mock(GetNewsUsecase::class.java)
    private val newsMapper = NewsMapper(DataFactory.getLocale().getUTCDateTimeFormatter(), SourceMapper())

    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun before(){
        newsViewModel = NewsViewModel(newsMapper, getNewsUsecase)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getNews() = runBlockingTest{
        val newsModel = NewsDataFactory.makeNewsModel()
        stubGetNewsUsecase(listOf(newsModel))

        val news = listOf(newsMapper.mapToView(newsModel))

        newsViewModel.getNews()

        val result = newsViewModel.news.getOrAwaitValue()
        Assert.assertEquals(Result.Success(news), result)

        newsViewModel.setErrorLayoutVisibility(isVisible = false)
        val isErrorLayoutVisible = newsViewModel.isErrorLayoutVisible.getOrAwaitValue()
        Assert.assertEquals(false, isErrorLayoutVisible.getContentIfNotHandled())
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getNewsError() = runBlockingTest{
        val exception = Exception()
        stubGetNewsUsecaseError(exception)

        newsViewModel.getNews()

        val result = newsViewModel.news.getOrAwaitValue()
        Assert.assertEquals(Result.Error(exception), result)

        newsViewModel.setErrorLayoutVisibility(isVisible = true)
        val isErrorLayoutVisible = newsViewModel.isErrorLayoutVisible.getOrAwaitValue()
        Assert.assertEquals(true, isErrorLayoutVisible.getContentIfNotHandled())
    }

    fun stubGetNewsUsecase(news: List<NewsModel>) = runBlocking{
        `when`(getNewsUsecase.invoke()).thenReturn(NetworkResult.Success(news))
    }
    fun stubGetNewsUsecaseError(exception: Exception) = runBlocking{
        `when`(getNewsUsecase.invoke()).thenReturn(NetworkResult.Error(exception))
    }
}