package news.app.com.test.factory

import news.app.com.domain.models.NewsModel
import news.app.com.domain.models.SourceModel
import news.app.com.ui.models.News
import news.app.com.ui.models.Source
import java.util.*

object NewsDataFactory {

    fun makeNewsModel(): NewsModel{
        return NewsModel(
            title = DummyDataFactory.randomString(),
            url = makeRandomArticleUrl(),
            summary = DummyDataFactory.randomString(),
            publishedDate = DummyDataFactory.randomDate(),
            writer = DummyDataFactory.randomString(),
            image = makeRandomImageUrl(),
            source = makeSourceModel()
        )
    }

    fun makeSourceModel(): SourceModel{
        return SourceModel(
            id = DummyDataFactory.randomString(),
            name = DummyDataFactory.randomString()
        )
    }

    fun makeNewsWith(articleLink: String): News {
        return News(
            title = DummyDataFactory.randomString(),
            summary = DummyDataFactory.randomString(),
            articleUrl = articleLink,
            publishedDate = Date(),
            writer = DummyDataFactory.randomString(),
            image = makeRandomImageUrl(),
            source = makeSource()
        )
    }

    fun makeSource(): Source {
        return Source(
            id = DummyDataFactory.randomString(),
            name = DummyDataFactory.randomString()
        )
    }

    fun makeRandomImageUrl():String{
        val imageString = DummyDataFactory.randomString()
        return "http://static01.nyt.com/images/" +
            "${DummyDataFactory.randomYear()}/${DummyDataFactory.randomMonth()}/${DummyDataFactory.randomDay()}/business/" +
            "$imageString/$imageString-${DummyDataFactory.randomString()}.jpg"
    }

    fun makeRandomArticleUrl():String {
        return "http://www.nytimes.com/" +
            "${DummyDataFactory.randomYear()}/${DummyDataFactory.randomMonth()}/${DummyDataFactory.randomDay()}/business/" +
            "${DummyDataFactory.randomString()}.html"
    }
}