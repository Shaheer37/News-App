package news.app.com.data.mapper

import news.app.com.data.MediaMapper
import news.app.com.data.NewsMapper
import news.app.com.data.test.factory.NewsDataFactory
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsAndMediaMapperTest{

    private val newsMapper = NewsMapper(MediaMapper())

    @Test
    fun testNewsNoMedia(){
        val newsData = NewsDataFactory.makeNewsEntityWithNoImage()
        val newsModel = newsMapper.mapToDomain(newsData)

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.articleUrl, newsModel.url)
        assertEquals(newsData.abstract, newsModel.summary)
        assertEquals(newsData.writer, newsModel.writer)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(null, newsModel.thumbImage)
        assertEquals(null, newsModel.articleImage)
    }

    @Test
    fun testNewsAllThumbAllArticleImage(){
        val newsData = NewsDataFactory.makeNewsEntityWithAllThumbAllArticle()
        val newsModel = newsMapper.mapToDomain(newsData)

        val expectedThumbUrl = newsData.media.first().mediaMetadata.find { it.format == MediaMapper.LARGE_THUMB }?.url
        val expectedArticleUrl = newsData.media.first().mediaMetadata.find { it.format == MediaMapper.MEDIUM_3BY2_440 }?.url

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.articleUrl, newsModel.url)
        assertEquals(newsData.abstract, newsModel.summary)
        assertEquals(newsData.writer, newsModel.writer)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(expectedThumbUrl, newsModel.thumbImage!!.url)
        assertEquals(newsData.media.first().caption, newsModel.thumbImage!!.caption)
        assertEquals(expectedArticleUrl, newsModel.articleImage!!.url)
        assertEquals(newsData.media.first().caption, newsModel.articleImage!!.caption)
    }

    @Test
    fun testNewsNoLargeThumbNo3by2Article(){
        val newsData = NewsDataFactory.makeNewsEntityWithNoLargeThumbImageNo3by2Article()
        val newsModel = newsMapper.mapToDomain(newsData)

        val expectedThumbUrl = newsData.media.first().mediaMetadata.find { it.format == MediaMapper.THUMB }?.url
        val expectedArticleUrl = newsData.media.first().mediaMetadata.find { it.format == MediaMapper.INLINE_ARTICLE }?.url

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.articleUrl, newsModel.url)
        assertEquals(newsData.abstract, newsModel.summary)
        assertEquals(newsData.writer, newsModel.writer)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(expectedThumbUrl, newsModel.thumbImage!!.url)
        assertEquals(newsData.media.first().caption, newsModel.thumbImage!!.caption)
        assertEquals(expectedArticleUrl, newsModel.articleImage!!.url)
        assertEquals(newsData.media.first().caption, newsModel.articleImage!!.caption)
    }

    @Test
    fun testNewsAllThumbNo3by2440Article(){
        val newsData = NewsDataFactory.makeNewsEntityWithAllThumbImageNo3by2440Article()
        val newsModel = newsMapper.mapToDomain(newsData)

        val expectedThumbUrl = newsData.media.first().mediaMetadata.find { it.format == MediaMapper.LARGE_THUMB }?.url
        val expectedArticleUrl = newsData.media.first().mediaMetadata.find { it.format == MediaMapper.MEDIUM_3BY2 }?.url

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.articleUrl, newsModel.url)
        assertEquals(newsData.abstract, newsModel.summary)
        assertEquals(newsData.writer, newsModel.writer)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(expectedThumbUrl, newsModel.thumbImage!!.url)
        assertEquals(newsData.media.first().caption, newsModel.thumbImage!!.caption)
        assertEquals(expectedArticleUrl, newsModel.articleImage!!.url)
        assertEquals(newsData.media.first().caption, newsModel.articleImage!!.caption)
    }
}