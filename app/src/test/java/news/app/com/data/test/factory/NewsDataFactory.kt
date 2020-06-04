package news.app.com.data.test.factory

import android.provider.ContactsContract
import news.app.com.data.*
import news.app.com.domain.models.SourceModel
import news.app.com.test.factory.DataFactory

object NewsDataFactory {

    fun makeNewsResponse(totalResults: Int, newsList: List<NewsEntity>): NewsResponse{
        return NewsResponse(
            status = "OK",
            totalResults = totalResults,
            articles = newsList
        )
    }

    fun makeFailedNewsResponse(): NewsResponse{
        return NewsResponse()
    }

    fun makeNewsEntity(): NewsEntity {
        return NewsEntity(
            title = DataFactory.randomString(),
            description = DataFactory.randomString(),
            author = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            url = makeRandomArticleUrl(),
            imageUrl = DataFactory.randomLink(),
            source = makeNewsSourceEntity()
        )
    }

    fun makeNewsSourceEntity(): SourceEntity {
        return SourceEntity(
                id = DataFactory.randomString(),
                name = DataFactory.randomString()
        )
    }

    fun makeRandomImageUrl(identifier: String):String{
        val imageString = DataFactory.randomString()
        return "http://static01.nyt.com/images/" +
            "${DataFactory.randomYear()}/${DataFactory.randomMonth()}/${DataFactory.randomDay()}/business/" +
            "$imageString/$imageString-$identifier.jpg"
    }

    fun makeRandomArticleUrl():String {
        return "http://www.nytimes.com/" +
            "${DataFactory.randomYear()}/${DataFactory.randomMonth()}/${DataFactory.randomDay()}/business/" +
            "${DataFactory.randomString()}.html"
    }
}