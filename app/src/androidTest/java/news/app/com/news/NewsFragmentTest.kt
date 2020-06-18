package news.app.com.news

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import news.app.com.MockServerDispatcher
import news.app.com.MockServerDispatcher.readFromFile
import news.app.com.R
import news.app.com.test.factory.DummyDataFactory
import news.app.com.ui.models.News
import news.app.com.ui.models.Source
import news.app.com.ui.news.MainActivity
import news.app.com.ui.news.viewnews.NewsAdapter
import news.app.com.ui.news.viewnews.NewsLoadStateAdapter
import news.app.com.ui.utils.EspressoIdlingResource
import news.app.com.ui.utils.getUTCDateTimeFormatter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class NewsFragmentTest {

    var webServer = MockWebServer()

    @Before
    fun before(){
        webServer.start(8080)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
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
                publishedDate = simpleDateFormat.parse("2020-06-18T10:17:27Z"),
                image = "https://assets1.ignimgs.com/2020/04/10/ps5dualsense-blogroll-1586549846553.jpg?width=1280",
                source = Source(id = "ign", name = "IGN")
        )

        val news20Title = "Spencer Torkelson should move fast through minors for Detroit Tigers. But where will he play? - Detroit Free Press"

        val lastNewsTitle = "First American woman to walk in space now first woman to reach deepest point of ocean - NBC News"

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

        pressBack()

        onView(withId(R.id.news_list))
                .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(2))
                .check(matches(hasDescendant(withText(news.title))))


        onView(withId(R.id.news_list))
                .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(19))
                .check(matches(hasDescendant(withText(news20Title))))

        onView(withId(R.id.news_list))
                .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(37))
                .check(matches(hasDescendant(withText(lastNewsTitle))))
    }

    @Test
    fun testNewsFailure(){
        webServer.enqueue(MockResponse().setResponseCode(404))
        webServer.enqueue(MockResponse().setResponseCode(200).setBody(readFromFile("/page1.json")))
        webServer.enqueue(MockResponse().setResponseCode(404))
        webServer.enqueue(MockResponse().setResponseCode(200).setBody(readFromFile("/page2.json")))

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.news_error_msg)).check(matches(isDisplayed()))
        onView(withId(R.id.retry_btn)).perform(click())

        val news3Title = "PS5 Price Listed on Amazon UK Was 'an Error' - IGN - IGN"
        val news19Title = "Spencer Torkelson should move fast through minors for Detroit Tigers. But where will he play? - Detroit Free Press"
        val lastNewsTitle = "First American woman to walk in space now first woman to reach deepest point of ocean - NBC News"

        onView(withId(R.id.news_list))
                .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(2))
                .check(matches(hasDescendant(withText(news3Title))))

        onView(withId(R.id.news_list))
                .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(19))
                .check(matches(hasDescendant(withText(news19Title))))

        onView(withId(R.id.news_list))
                .perform(RecyclerViewActions.scrollToPosition<NewsLoadStateAdapter.LoadStateViewHolder>(20))
                .check(matches(hasDescendant(withId(R.id.retry_button))))

        onView(withId(R.id.retry_button)).perform(click())

        onView(withId(R.id.news_list))
                .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(37))
                .check(matches(hasDescendant(withText(lastNewsTitle))))

    }

}