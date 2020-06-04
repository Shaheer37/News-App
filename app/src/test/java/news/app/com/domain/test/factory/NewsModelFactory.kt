package news.app.com.domain.test.factory

import news.app.com.domain.models.NewsModel
import news.app.com.domain.models.SourceModel
import news.app.com.test.factory.DataFactory

object NewsModelFactory {
    fun makeNewsModel(): NewsModel {
        return NewsModel(
            title = DataFactory.randomString(),
            summary = DataFactory.randomString(),
            url = DataFactory.randomString(),
            writer = DataFactory.randomString(),
            publishedDate = DataFactory.randomString(),
            image = DataFactory.randomLink(),
            source = makeNewsSourceModel()
        )

    }

    fun makeNewsSourceModel(): SourceModel{
        return SourceModel(
            id = DataFactory.randomString(),
            name = DataFactory.randomString()
        )
    }
}