package news.app.com.data.localdb

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import news.app.com.data.persistence.News
import news.app.com.data.persistence.NewsDatabase
import news.app.com.data.persistence.Source
import news.app.com.test.factory.DataFactory
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
@Config(sdk = [Build.VERSION_CODES.P])
class NewsDaoTest {
    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NewsDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                NewsDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Test
    fun testInsertNewsAndGetNewsDetails(){
        val news = News(
                7153292,
                "SpaceX Launch: Highlights From the Weather-Delayed Mission",
                "Follow SpaceX's launch of NASA astronauts aboard the Crew Dragon spacecraft.",
                "https://www.nytimes.com/2020/05/27/science/spacex-launch-nasa.html",
                "Shaheer",
                "2020-05-27T03:56:30Z",
                DataFactory.randomLink(),
                Source(
                        DataFactory.randomString(),
                        DataFactory.randomString()
                )
        )
        database.newsDao().insertNews(news)

        val newsList = database.newsDao().getNews()
        Assert.assertEquals(1, newsList.size)
        for(dbNews in newsList){
            Assert.assertEquals(news.newsId, dbNews.newsId)
            Assert.assertEquals(news.title, dbNews.title)
            Assert.assertEquals(news.description, dbNews.description)
            Assert.assertEquals(news.url, dbNews.url)
            Assert.assertEquals(news.author, dbNews.author)
            Assert.assertEquals(news.publishedDate, dbNews.publishedDate)
            Assert.assertEquals(news.imageUrl, dbNews.imageUrl)
            Assert.assertEquals(news.source.sourceId, dbNews.source.sourceId)
            Assert.assertEquals(news.source.sourceName, dbNews.source.sourceName)
        }

        database.newsDao().deleteNews()
        val noNews = database.newsDao().getNews()
        Assert.assertEquals(0, noNews.size)
    }

    @After
    fun closeDb() = database.close()

}