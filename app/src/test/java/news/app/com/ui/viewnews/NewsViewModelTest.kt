package news.app.com.ui.viewnews

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
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
import news.app.com.domain.executor.PostExecutionThread
import news.app.com.domain.models.NewsModel
import news.app.com.test.factory.DataFactory
import news.app.com.ui.NewsMapper
import news.app.com.ui.SourceMapper
import news.app.com.ui.TestThread
import news.app.com.ui.getOrAwaitValue
import news.app.com.ui.models.News
import news.app.com.ui.news.viewnews.NewsViewModel
import news.app.com.ui.test.factory.NewsDataFactory
import news.app.com.ui.utils.getUTCDateTimeFormatter
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class NewsViewModelTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    private val getNewsUsecase = mock(GetNewsUsecaseImpl::class.java)

    private val newsMapper = NewsMapper(DataFactory.getLocale().getUTCDateTimeFormatter(), SourceMapper())

    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun before(){
        newsViewModel = NewsViewModel(newsMapper, getNewsUsecase)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getNews() = runBlockingTest{
        newsViewModel.getNews()

        verify(getNewsUsecase).invoke()
    }

    @Test
    fun testErrorLayoutLivedata(){
        newsViewModel.setErrorLayoutVisibility(true)
        Assert.assertEquals(true, newsViewModel.isErrorLayoutVisible.getOrAwaitValue().peekContent())

        newsViewModel.setErrorLayoutVisibility(false)
        Assert.assertEquals(false, newsViewModel.isErrorLayoutVisible.getOrAwaitValue().peekContent())
    }
}