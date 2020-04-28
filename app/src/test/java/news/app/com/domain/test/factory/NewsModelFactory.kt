package news.app.com.domain.test.factory

import news.app.com.domain.models.ImageModel
import news.app.com.domain.models.NewsModel
import news.app.com.test.factory.DataFactory

object NewsModelFactory {
    fun makeNewsModel(): NewsModel {
        return NewsModel(
            title = DataFactory.randomString(),
            summary = DataFactory.randomString(),
            url = DataFactory.randomString(),
            writer = DataFactory.randomString(),
            publishedDate = DataFactory.randomString(),
            articleImage = makeNewsImageModel(),
            thumbImage = makeNewsImageModel()
        )

    }

    fun makeNewsImageModel(): ImageModel{
        return ImageModel(
            url = DataFactory.randomString(),
            caption = DataFactory.randomString()
        )
    }
}