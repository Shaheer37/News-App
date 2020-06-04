package news.app.com.ui.test.factory

import android.provider.ContactsContract
import news.app.com.domain.models.NewsModel
import news.app.com.domain.models.SourceModel
import news.app.com.test.factory.DataFactory
import news.app.com.ui.models.News
import news.app.com.ui.models.Source
import java.util.*

object NewsDataFactory {

    fun makeNewsModel(): NewsModel{
        return NewsModel(
            title = DataFactory.randomString(),
            url = makeRandomArticleUrl(),
            summary = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            writer = DataFactory.randomString(),
            image = DataFactory.randomLink(),
            source = makeSourceModel()
        )
    }

    fun makeSourceModel(): SourceModel {
        return SourceModel(
            id = DataFactory.randomString(),
            name = DataFactory.randomString()
        )
    }

    fun makeNews(): News {
        return News(
            title = DataFactory.randomString(),
            summary = DataFactory.randomString(),
            articleUrl = makeRandomArticleUrl(),
            writer = DataFactory.randomString(),
            publishedDate = Date(),
            image = makeRandomImageUrl(),
            source = makeSource()
        )
    }

    fun makeSource(): Source {
        return Source(
            id = DataFactory.randomString(),
            name = DataFactory.randomString()
        )
    }
    fun makeRandomImageUrl():String{
        val imageString = DataFactory.randomString()
        return "http://static01.nyt.com/images/" +
            "${DataFactory.randomYear()}/${DataFactory.randomMonth()}/${DataFactory.randomDay()}/business/" +
            "$imageString/$imageString-${DataFactory.randomString()}.jpg"
    }

    fun makeRandomArticleUrl():String {
        return "http://www.nytimes.com/" +
            "${DataFactory.randomYear()}/${DataFactory.randomMonth()}/${DataFactory.randomDay()}/business/" +
            "${DataFactory.randomString()}.html"
    }
}