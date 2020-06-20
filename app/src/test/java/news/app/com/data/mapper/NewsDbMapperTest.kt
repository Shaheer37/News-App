package news.app.com.data.mapper

import news.app.com.data.NewsDbMapper
import news.app.com.data.SourceDbMapper
import news.app.com.data.test.factory.NewsDataFactory
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsDbMapperTest{

    private val newsDbMapper
            = NewsDbMapper(SourceDbMapper())

    @Test
    fun testNewsDbMapping(){
        val newsData = NewsDataFactory.makeNewsEntity()
        val newsModel = newsDbMapper.map(newsData)

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.description, newsModel.description)
        assertEquals(newsData.url, newsModel.url)
        assertEquals(newsData.author, newsModel.author)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(newsData.imageUrl, newsModel.imageUrl)
        assertEquals(newsData.source?.id, newsModel.source?.sourceId)
        assertEquals(newsData.source?.name, newsModel.source?.sourceName)
    }

    @Test
    fun testNewsDbMappingNoSource(){
        val newsData = NewsDataFactory.makeNewsEntityNoSource()
        val newsModel = newsDbMapper.map(newsData)

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.description, newsModel.description)
        assertEquals(newsData.url, newsModel.url)
        assertEquals("",newsModel.author)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(newsData.imageUrl, newsModel.imageUrl)
        assertNull(newsModel.source)
    }

    @Test
    fun testNewsDbMappingNullSourceId() {
        val newsData = NewsDataFactory.makeNewsEntityNullSourceId()
        val newsModel = newsDbMapper.map(newsData)

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.description, newsModel.description)
        assertEquals(newsData.url, newsModel.url)
        assertEquals(newsData.author, newsModel.author)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(newsData.imageUrl, newsModel.imageUrl)
        assertNull(newsModel.source)
    }

    @Test
    fun testNewsDbMappingNullSourceName(){
        val newsData = NewsDataFactory.makeNewsEntityNullSourceName()
        val newsModel = newsDbMapper.map(newsData)

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.description, newsModel.description)
        assertEquals(newsData.url, newsModel.url)
        assertEquals(newsData.author, newsModel.author)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(newsData.imageUrl, newsModel.imageUrl)
        assertNull(newsModel.source)
    }
}