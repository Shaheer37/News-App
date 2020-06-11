package news.app.com.data.mapper

import news.app.com.data.NewsDbMapper
import news.app.com.data.NewsMapper
import news.app.com.data.SourceDbMapper
import news.app.com.data.SourceMapper
import news.app.com.data.test.factory.NewsDataFactory
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsMapperTest{

    private val newsMapper
            = NewsMapper(SourceMapper())

    @Test
    fun testNewsDbMapping(){
        val newsData = NewsDataFactory.makeNews()
        val newsModel = newsMapper.map(newsData)

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.description, newsModel.summary)
        assertEquals(newsData.url, newsModel.url)
        assertEquals(newsData.author, newsModel.writer)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(newsData.imageUrl, newsModel.image)
        assertEquals(newsData.source?.sourceId, newsModel.source?.id)
        assertEquals(newsData.source?.sourceName, newsModel.source?.name)
    }

    @Test
    fun testNewsDbMappingNoSource(){
        val newsData = NewsDataFactory.makeNewsNoSource()
        val newsModel = newsMapper.map(newsData)

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.description, newsModel.summary)
        assertEquals(newsData.url, newsModel.url)
        assertNull("Author is not null", newsModel.writer)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(newsData.imageUrl, newsModel.image)
        assertNull("Source is not null", newsModel.source)
    }
}