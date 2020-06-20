package news.app.com

import news.app.com.navigation.NavigationTest
import news.app.com.news.NewsFragmentTest
import news.app.com.newsdetails.TestNewsDetailsFragment
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        NavigationTest::class,
        NewsFragmentTest::class,
        TestNewsDetailsFragment::class
)
class TestSuite {
}