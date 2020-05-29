package news.app.com.data.localdb

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import news.app.com.data.persistence.Media
import news.app.com.data.persistence.MediaMetadata
import news.app.com.data.persistence.News
import news.app.com.data.persistence.NewsDatabase
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
                "100000007153292",
                "SpaceX Launch: Highlights From the Weather-Delayed Mission",
                "Follow SpaceX's launch of NASA astronauts aboard the Crew Dragon spacecraft.",
                "https://www.nytimes.com/2020/05/27/science/spacex-launch-nasa.html",
                "",
                "2020-05-27"
        )
        database.newsDao().insertNews(news)

        val media = Media(
            0,
            "100000007153292",
            "image",
            "photo",
            "Ominous clouds over the SpaceX Falcon 9 rocket and Crew Dragon Spacecraft at Cape Canaveral, Fla., on Wednesday.",
            "Joe Raedle/Getty Images",
            1
        )
        val mediaId = database.newsDao().insertMedia(media)
        println("Inserted Media Id $mediaId")

        val metadata = MediaMetadata(
                0,
                mediaId,
                "https://static01.nyt.com/images/2020/05/27/science/27LAUNCH-LIVE-postponement/27LAUNCH-LIVE-postponement-thumbStandard.jpg",
                "Standard Thumbnail",
                75,
                75
        )
        val metadataId = database.newsDao().insertMetadata(metadata)
        println( "Inserted Metadata Id $metadataId")

        val newsDetails = database.newsDao().getNews()
        Assert.assertEquals(1, newsDetails.size)
        for(newsDetail in newsDetails){
            Assert.assertEquals(news.newsId, newsDetail.news.newsId)
            Assert.assertEquals(news.title, newsDetail.news.title)
            Assert.assertEquals(news.subtitle, newsDetail.news.subtitle)
            Assert.assertEquals(news.articleUrl, newsDetail.news.articleUrl)
            Assert.assertEquals(news.writer, newsDetail.news.writer)
            Assert.assertEquals(news.publishedDate, newsDetail.news.publishedDate)

            for(mediaDetails in newsDetail.media){
                Assert.assertEquals(mediaId, mediaDetails.media.mediaId)
                Assert.assertEquals(news.newsId, mediaDetails.media.newsId)
                Assert.assertEquals(media.type, mediaDetails.media.type)
                Assert.assertEquals(media.subtype, mediaDetails.media.subtype)
                Assert.assertEquals(media.caption, mediaDetails.media.caption)
                Assert.assertEquals(media.copyright, mediaDetails.media.copyright)
                Assert.assertEquals(media.approvedFroSyndication, mediaDetails.media.approvedFroSyndication)

                for(mediaMetadata in mediaDetails.metaData){
                    Assert.assertEquals(mediaId, mediaMetadata.mediaId)
                    Assert.assertEquals(metadataId, mediaMetadata.metadataId)
                    Assert.assertEquals(mediaMetadata.url, mediaMetadata.url)
                    Assert.assertEquals(mediaMetadata.format, mediaMetadata.format)
                    Assert.assertEquals(mediaMetadata.height, mediaMetadata.height)
                    Assert.assertEquals(mediaMetadata.width, mediaMetadata.width)
                }
            }
        }

        database.newsDao().deleteNews()
        val noNews = database.newsDao().getNews()
        Assert.assertEquals(0, noNews.size)
        val noMedia = database.newsDao().getMedia()
        Assert.assertEquals(0, noMedia.size)
    }

    @After
    fun closeDb() = database.close()

}