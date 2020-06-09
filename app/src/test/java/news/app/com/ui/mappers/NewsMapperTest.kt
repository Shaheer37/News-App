package news.app.com.ui.mappers

import news.app.com.test.factory.DataFactory
import news.app.com.test.factory.DataFactory.dateFormat
import news.app.com.ui.NewsMapper
import news.app.com.ui.SourceMapper
import news.app.com.ui.test.factory.NewsDataFactory
import news.app.com.ui.utils.getUTCDateTimeFormatter
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat


class NewsMapperTest {

    private val newsMapper = NewsMapper(DataFactory.getLocale().getUTCDateTimeFormatter(), SourceMapper())

    @Test
    fun testMapper(){
        val newsModel = NewsDataFactory.makeNewsModel()

        val actualMappedNews = newsMapper.mapToView(newsModel)

        Assert.assertEquals(newsModel.url, actualMappedNews.articleUrl)
        Assert.assertEquals(newsModel.title, actualMappedNews.title)
        Assert.assertEquals(newsModel.summary, actualMappedNews.summary)
        Assert.assertEquals(newsModel.image, actualMappedNews.image)
        Assert.assertEquals(SimpleDateFormat(dateFormat).parse(newsModel.publishedDate), actualMappedNews.publishedDate)
        Assert.assertEquals(newsModel.source?.id, actualMappedNews.source?.id)
        Assert.assertEquals(newsModel.source?.name, actualMappedNews.source?.name)
    }
}