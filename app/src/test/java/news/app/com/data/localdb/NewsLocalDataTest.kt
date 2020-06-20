package news.app.com.data.localdb

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import news.app.com.data.persistence.*
import news.app.com.data.test.factory.NewsDataFactory
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
@Config(sdk = [Build.VERSION_CODES.P])
class NewsLocalDataTest {
    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NewsDatabase

    private lateinit var localdata: NewsLocalData

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                NewsDatabase::class.java
        ).allowMainThreadQueries().build()

        localdata = NewsLocalData(database)
    }

    @Test
    fun testInsertNewsAndGetNewsDetails() = runBlockingTest{
        val news1 = NewsDataFactory.makeNews()
        val newsKey1 = RemoteKeys(news1.title,null,2)

        val news2 = NewsDataFactory.makeNews()
        val newsKey2 = RemoteKeys(news2.title,1,3)

        val news3 = NewsDataFactory.makeNews()
        val newsKey3 = RemoteKeys(news3.title,2,null)

        val newsDataList = mutableListOf(news1)
        val keyDataList = mutableListOf(newsKey1)

        assertions(newsDataList, keyDataList)

        newsDataList += news2
        keyDataList += newsKey2

        assertions(newsDataList, keyDataList)

        newsDataList += news3
        keyDataList += newsKey3

        assertions(newsDataList, keyDataList)

        localdata.clearDatabase()
        val noNews = localdata.getNewsList()
        Assert.assertEquals(0, noNews.size)
    }

    private suspend fun assertions(newsDataList: List<News>, keyDataList: List<RemoteKeys>){
        localdata.insertNews(newsDataList)
        localdata.insertNewsKeys(keyDataList)

        val newsList = localdata.getNewsList()
        Assert.assertEquals(newsDataList.size, newsList.size)
        for(i in newsList.indices){
            Assert.assertEquals(newsDataList[i], newsList[i])
            val newsKey = localdata.getNewsKeyForNewsTitle(newsList[i].title)
            Assert.assertNotNull(newsKey)
            Assert.assertEquals(keyDataList[i], newsKey)
        }
    }

    @After
    fun closeDb() = database.close()

}