package news.app.com.navigation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import news.app.com.MockServerDispatcher
import news.app.com.R
import news.app.com.ui.news.MainActivity
import news.app.com.ui.news.viewnews.NewsAdapter
import news.app.com.ui.utils.EspressoIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTest {

    var webServer = MockWebServer()

    @Before
    fun registerIdlingResource(){
        webServer.start(8080)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        webServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testNavigation(){
        webServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.news_list)).check(RecyclerViewItemCount(greaterThan(0)))

        onView(withId(R.id.news_list))
            .perform(actionOnItemAtPosition<NewsAdapter.NewsHolder>(0, click()))

        onView(withId(R.id.news_summary)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.news_title), withParent(withId(R.id.cl_news_details)))).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.news_image), withParent(withId(R.id.cl_news_details)))).check(matches(isDisplayed()))
        onView(withId(R.id.news_article_link_btn)).check(matches(isDisplayed()))
    }
}

class RecyclerViewItemCount(private val matcher: Matcher<Int>): ViewAssertion{

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if(view != null && view is RecyclerView){
            assertThat(view.adapter?.itemCount, matcher)
        }
    }
}