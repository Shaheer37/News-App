package news.app.com.newsdetails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import news.app.com.test.factory.NewsDataFactory
import news.app.com.ui.news.newsdetails.NewsDetailFragment
import org.junit.Test
import org.junit.runner.RunWith
import news.app.com.R
import org.junit.After
import org.junit.Before



@RunWith(AndroidJUnit4ClassRunner::class)
class TestNewsDetailsFragment {

    @Before
    fun before(){
        Intents.init()
    }

    @After
    fun after(){
        Intents.release()
    }


    @Test
    fun testNewsDetailsFragment(){
        val news = NewsDataFactory.makeNewsWith(
            "https://www.nytimes.com/2015/08/18/business/work-policies-may-be-kinder-but-brutal-competition-isnt.html"
        )

        val newsBundle = Bundle()
        newsBundle.putParcelable("news",news)
        val scenario = launchFragmentInContainer<NewsDetailFragment>(newsBundle, R.style.AppTheme)


        onView(withId(R.id.news_title)).check(matches(withText(news.title)))
        onView(withId(R.id.news_summary)).check(matches(withText(news.summary)))
        onView(withId(R.id.news_article_link_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.news_image)).check(matches(isDisplayed()))
        onView(withId(R.id.news_image)).check(matches(withContentDescription(news.getImageDescription())))

        onView(withId(R.id.news_article_link_btn)).perform(click())

        Intents.intended(hasAction(Intent.ACTION_VIEW))
        Intents.intended(hasData(news.articleUrl))
    }

}