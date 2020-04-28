package news.app.com.ui.newsdetails

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import news.app.com.ui.getOrAwaitValue
import news.app.com.ui.news.newsdetails.NewsDetailViewModel
import news.app.com.ui.test.factory.NewsDataFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class NewsDetailsViewModelTest {

    private val newsDetailsViewModel = NewsDetailViewModel()

    @Test
    fun newsDetailTest(){
        val news = NewsDataFactory.makeNews()

        newsDetailsViewModel.setNewsData(news)

        Assert.assertEquals(news.title, newsDetailsViewModel.newsTitle.getOrAwaitValue())
        Assert.assertEquals(news.summary, newsDetailsViewModel.newsSummary.getOrAwaitValue())
        Assert.assertEquals(news.articleImage, newsDetailsViewModel.newsImage.getOrAwaitValue())

        newsDetailsViewModel.onArticleLinkClicked()
        Assert.assertEquals(news.articleUrl, newsDetailsViewModel.onOpenNewsArticle.getOrAwaitValue().peekContent())
    }
}