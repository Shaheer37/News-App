package news.app.com.ui.test.factory

import news.app.com.domain.models.ImageModel
import news.app.com.domain.models.NewsModel
import news.app.com.test.factory.DataFactory
import news.app.com.ui.models.Image
import news.app.com.ui.models.News

object NewsDataFactory {

    fun makeNewsModel(): NewsModel{
        return NewsModel(
            title = DataFactory.randomString(),
            url = makeRandomArticleUrl(),
            summary = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            writer = DataFactory.randomString(),
            thumbImage = makeImageModel(),
            articleImage = makeImageModel()
        )
    }

    fun makeImageModel(): ImageModel{
        return ImageModel(
            url = makeRandomImageUrl(),
            caption = DataFactory.randomString()
        )
    }

    fun makeNews(): News {
        return News(
            title = DataFactory.randomString(),
            summary = DataFactory.randomString(),
            articleUrl = makeRandomArticleUrl(),
            thumbImage = makeImage(),
            articleImage = makeImage()
        )
    }

    fun makeImage(): Image {
        return Image(
            url = makeRandomImageUrl(),
            caption = DataFactory.randomString()
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