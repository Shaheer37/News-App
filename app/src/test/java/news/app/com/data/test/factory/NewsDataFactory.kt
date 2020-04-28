package news.app.com.data.test.factory

import news.app.com.data.*
import news.app.com.test.factory.DataFactory

object NewsDataFactory {

    fun makeNewsResponse(newsList: List<NewsEntity>): NewsResponse{
        return NewsResponse(
            status = "OK",
            results = newsList
        )
    }

    fun makeFailedNewsResponse(): NewsResponse{
        return NewsResponse()
    }

    fun makeNewsEntityWithNoImage(): NewsEntity {
        return NewsEntity(
            title = DataFactory.randomString(),
            abstract = DataFactory.randomString(),
            writer = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            articleUrl = makeRandomArticleUrl(),
            media = listOf()
        )
    }

    fun makeNewsEntityWithAllThumbAllArticle(): NewsEntity {
        return NewsEntity(
            title = DataFactory.randomString(),
            abstract = DataFactory.randomString(),
            writer = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            articleUrl = makeRandomArticleUrl(),
            media = listOf(makeMediaEntity_thumb_Article())
        )
    }

    fun makeNewsEntityWithNoLargeThumbImageNo3by2Article(): NewsEntity {
        return NewsEntity(
            title = DataFactory.randomString(),
            abstract = DataFactory.randomString(),
            writer = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            articleUrl = makeRandomArticleUrl(),
            media = listOf(makeMediaEntity_noLargeThumb_No3by2Article())
        )
    }

    fun makeNewsEntityWithAllThumbImageNo3by2440Article(): NewsEntity {
        return NewsEntity(
            title = DataFactory.randomString(),
            abstract = DataFactory.randomString(),
            writer = DataFactory.randomString(),
            publishedDate = DataFactory.randomDate(),
            articleUrl = makeRandomArticleUrl(),
            media = listOf(makeMediaEntity_AllThumb_No3by2440Article())
        )
    }

    fun makeMediaEntity_thumb_Article(): MediaEntity{
        return MediaEntity(
            caption = DataFactory.randomString(),
            type = "image",
            subtype = "photo",
            copyright = DataFactory.randomString(),
            approvedForSyndication = 1,
            mediaMetadata = listOf(
                makeThumbImage(),
                makeLargeThumbImage(),
                makeInlineArticleImage(),
                makeMedium3by2ArticleImage(),
                makeMedium3by2440ArticleImage()
            )
        )
    }
    fun makeMediaEntity_noLargeThumb_No3by2Article(): MediaEntity{
        return MediaEntity(
            caption = DataFactory.randomString(),
            type = "image",
            subtype = "photo",
            copyright = DataFactory.randomString(),
            approvedForSyndication = 1,
            mediaMetadata = listOf(
                makeThumbImage(),
                makeInlineArticleImage()
            )
        )
    }

    fun makeMediaEntity_AllThumb_No3by2440Article(): MediaEntity{
        return MediaEntity(
            caption = DataFactory.randomString(),
            type = "image",
            subtype = "photo",
            copyright = DataFactory.randomString(),
            approvedForSyndication = 1,
            mediaMetadata = listOf(
                makeThumbImage(),
                makeLargeThumbImage(),
                makeInlineArticleImage(),
                makeMedium3by2ArticleImage()
            )
        )
    }
    fun makeThumbImage(): MediaMetadataEntity{
        return MediaMetadataEntity(
            url = makeRandomImageUrl("thumbStandard"),
            format = MediaMapper.THUMB,
            height = 75,
            width = 75
        )
    }

    fun makeLargeThumbImage(): MediaMetadataEntity{
        return MediaMetadataEntity(
            url = makeRandomImageUrl("thumbLarge"),
            format = MediaMapper.LARGE_THUMB,
            height = 150,
            width = 150
        )
    }

    fun makeInlineArticleImage(): MediaMetadataEntity{
        return MediaMetadataEntity(
            url = makeRandomImageUrl("articleInline"),
            format = MediaMapper.INLINE_ARTICLE,
            height = 127,
            width = 190
        )
    }

    fun makeMedium3by2ArticleImage(): MediaMetadataEntity{
        return MediaMetadataEntity(
            url = makeRandomImageUrl("mediumThreeByTwo210"),
            format = MediaMapper.MEDIUM_3BY2,
            height = 140,
            width = 210
        )
    }

    fun makeMedium3by2440ArticleImage(): MediaMetadataEntity{
        return MediaMetadataEntity(
            url = makeRandomImageUrl("mediumThreeByTwo440"),
            format = MediaMapper.MEDIUM_3BY2_440,
            height = 140,
            width = 210
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