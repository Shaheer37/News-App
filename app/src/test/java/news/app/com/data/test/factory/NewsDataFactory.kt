package news.app.com.data.test.factory

import android.provider.ContactsContract
import news.app.com.data.*
import news.app.com.data.persistence.News
import news.app.com.data.persistence.Source
import news.app.com.domain.models.SourceModel
import news.app.com.test.factory.DataFactory

object NewsDataFactory {

    fun makeNewsResponse(newsList: List<NewsEntity>): NewsResponse{
        return NewsResponse(
            status = "ok",
            totalResults = newsList.size,
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

    fun makeNewsEntityNoSource(): NewsEntity {
        return NewsEntity(
            title = DataFactory.randomString(),
            description = DataFactory.randomString(),
            author = null,
            publishedDate = DataFactory.randomDate(),
            url = makeRandomArticleUrl(),
            imageUrl = DataFactory.randomLink(),
            source = null
        )
    }

    fun makeNewsEntityNullSourceId(): NewsEntity {
        return NewsEntity(
            title = DataFactory.randomString(),
            description = DataFactory.randomString(),
            author = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            url = makeRandomArticleUrl(),
            imageUrl = DataFactory.randomLink(),
            source = makeNewsSourceEntityNullId()
        )
    }
    fun makeNewsEntityNullSourceName(): NewsEntity {
        return NewsEntity(
            title = DataFactory.randomString(),
            description = DataFactory.randomString(),
            author = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            url = makeRandomArticleUrl(),
            imageUrl = DataFactory.randomLink(),
            source = makeNewsSourceEntityNullName()
        )
    }

    fun makeNewsSourceEntity(): SourceEntity {
        return SourceEntity(
                id = DataFactory.randomString(),
                name = DataFactory.randomString()
        )
    }
    fun makeNewsSourceEntityNullId(): SourceEntity {
        return SourceEntity(
                id = null,
                name = DataFactory.randomString()
        )
    }

    fun makeNewsSourceEntityNullName(): SourceEntity {
        return SourceEntity(
                id = DataFactory.randomString(),
                name = null
        )
    }

    fun makeNews(): News {
        return News(
                title = DataFactory.randomString(),
                description = DataFactory.randomString(),
                author = DataFactory.randomString(),
                publishedDate = DataFactory.randomDate(),
                url = makeRandomArticleUrl(),
                imageUrl = DataFactory.randomLink(),
                source = makeNewsSource()
        )
    }

    fun makeNewsNoSource(): News {
        return News(
                title = DataFactory.randomString(),
                description = DataFactory.randomString(),
                author = null,
                publishedDate = DataFactory.randomDate(),
                url = makeRandomArticleUrl(),
                imageUrl = DataFactory.randomLink(),
                source = null
        )
    }

    fun makeNewsSource(): Source {
        return Source(
                sourceId = DataFactory.randomString(),
                sourceName = DataFactory.randomString()
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