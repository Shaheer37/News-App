package news.app.com.news

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import news.app.com.R
import news.app.com.domain.GetNewsUsecase
import news.app.com.domain.models.NewsModel
import news.app.com.test.factory.NewsDataFactory
import news.app.com.ui.NewsMapper
import news.app.com.ui.news.viewnews.NewsViewModel
import news.app.com.domain.models.NetworkResult
import news.app.com.ui.news.viewnews.NewsFragmentDirections
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import news.app.com.data.retrofit.DataFactory
import news.app.com.test.factory.DummyDataFactory
import news.app.com.ui.SourceMapper
import news.app.com.ui.injection.ViewModelFactory
import news.app.com.ui.news.viewnews.NewsAdapter
import news.app.com.ui.utils.EspressoIdlingResource
import news.app.com.ui.utils.getUTCDateTimeFormatter
import org.junit.After


@RunWith(AndroidJUnit4ClassRunner::class)
class NewsFragmentTest {

    private val newsMapper = NewsMapper(DummyDataFactory.randomLocale().getUTCDateTimeFormatter(), SourceMapper())
    private val getNewsUsecase = mock(GetNewsUsecase::class.java)

    private lateinit var newsViewModelFactory: ViewModelFactory

    @Before
    fun before(){
        newsViewModelFactory = ViewModelFactory(
            mapOf(
                NewsViewModel::class.java
                    to NewsViewModelProvider(newsMapper, getNewsUsecase)
            )
        )
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testNewsFragment() = runBlockingTest{

        val newsModels = listOf(
            NewsDataFactory.makeNewsModel(),
            NewsDataFactory.makeNewsModel(),
            NewsDataFactory.makeNewsModel()
        )
        stubGetNewsUsecase(newsModels)

        val news = newsMapper.mapToView(newsModels[2])

        NewsFragmentTestWrapper.newsViewModelFactory = newsViewModelFactory
        val scenario = launchFragmentInContainer <NewsFragmentTestWrapper>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.news_list))
            .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(2))
            .check(matches(hasDescendant(withText(news.title))))

        onView(withText(news.title)).perform(click())

        verify(navController).navigate(NewsFragmentDirections.actionNewsToNewsDetail(news))
    }

    suspend fun stubGetNewsUsecase(news: List<NewsModel>) {
        `when`(getNewsUsecase()).thenReturn(NetworkResult.Success(news))
    }

}