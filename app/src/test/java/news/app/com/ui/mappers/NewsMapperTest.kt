package news.app.com.ui.mappers

import news.app.com.ui.ImageMapper
import news.app.com.ui.NewsMapper
import news.app.com.ui.test.factory.NewsDataFactory
import org.junit.Assert
import org.junit.Test


class NewsMapperTest {

    private val newsMapper = NewsMapper(ImageMapper())

    @Test
    fun testMapper(){
        val newsModel = NewsDataFactory.makeNewsModel()

        val actualMappedNews = newsMapper.mapToView(newsModel)

        Assert.assertEquals(newsModel.url, actualMappedNews.articleUrl)
        Assert.assertEquals(newsModel.title, actualMappedNews.title)
        Assert.assertEquals(newsModel.summary, actualMappedNews.summary)
        Assert.assertEquals(newsModel.thumbImage!!.url, actualMappedNews.thumbImage!!.url)
        Assert.assertEquals(newsModel.thumbImage!!.caption, actualMappedNews.thumbImage!!.caption)
        Assert.assertEquals(newsModel.articleImage!!.url, actualMappedNews.articleImage!!.url)
        Assert.assertEquals(newsModel.articleImage!!.caption, actualMappedNews.articleImage!!.caption)
    }
}