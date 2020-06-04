package news.app.com.data.mapper

import news.app.com.data.SourceMapper
import news.app.com.data.NewsMapper
import news.app.com.data.test.factory.NewsDataFactory
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NewsAndMediaMapperTest{

    private val newsMapper = NewsMapper(SourceMapper())

    @Test
    fun testNewsNoMedia(){
        val newsData = NewsDataFactory.makeNewsEntity()
        val newsModel = newsMapper.mapToDomain(newsData)

        assertEquals(newsData.title, newsModel.title)
        assertEquals(newsData.description, newsModel.summary)
        assertEquals(newsData.url, newsModel.url)
        assertEquals(newsData.author, newsModel.writer)
        assertEquals(newsData.publishedDate, newsModel.publishedDate)
        assertEquals(newsData.imageUrl, newsModel.image)
        assertEquals(newsData.source.id, newsModel.source.id)
        assertEquals(newsData.source.name, newsModel.source.name)
    }
}