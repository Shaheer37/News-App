package news.app.com.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import news.app.com.R
import news.app.com.test.factory.DummyDataFactory
import news.app.com.ui.models.News
import news.app.com.ui.models.Source
import news.app.com.ui.news.MainActivity
import news.app.com.ui.news.viewnews.NewsAdapter
import news.app.com.ui.news.viewnews.NewsFragment
import news.app.com.ui.news.viewnews.NewsFragmentDirections
import news.app.com.ui.utils.EspressoIdlingResource
import news.app.com.ui.utils.getUTCDateTimeFormatter
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@RunWith(AndroidJUnit4ClassRunner::class)
class NewsFragmentTest {

    var webServer = MockWebServer()

    @Before
    fun before(){
        Intents.init()
        webServer.start(8080)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        Intents.release()
        webServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testNewsFragment() {
        webServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        val simpleDateFormat = DummyDataFactory.randomLocale().getUTCDateTimeFormatter()
        val news = News(
                title = "PS5 Price Listed on Amazon UK Was 'an Error' - IGN - IGN",
                articleUrl = "https://www.ign.com/articles/ps5-price-games-amazon-listing-error",
                summary = "After a £599.99 pricing for the console went live, Amazon UK has told IGN that listing was made in error.",
                writer = "Joe Skrebels",
                publishedDate = simpleDateFormat.parse("2020-06-11T10:17:27Z"),
                image = "https://assets1.ignimgs.com/2020/04/10/ps5dualsense-blogroll-1586549846553.jpg?width=1280",
                source = Source(id = "ign", name = "IGN")
        )

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.news_list))
            .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(2))
            .check(matches(hasDescendant(withText(news.title))))

        onView(withText(news.title)).perform(click())

        onView(withId(R.id.news_title)).check(matches(withText(news.title)))
        onView(withId(R.id.news_summary)).check(matches(withText(news.summary)))
        onView(withId(R.id.news_article_link_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.news_image)).check(matches(isDisplayed()))
        onView(withId(R.id.news_image)).check(matches(withContentDescription(news.getImageDescription())))

        onView(withId(R.id.news_article_link_btn)).perform(click())

        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
        Intents.intended(IntentMatchers.hasData(news.articleUrl))
    }

}