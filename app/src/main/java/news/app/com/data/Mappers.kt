package news.app.com.data

import news.app.com.domain.models.ImageModel
import news.app.com.domain.models.NewsModel
import timber.log.Timber
import javax.inject.Inject

interface Mapper<in E, out D> {
    fun mapToDomain(e: E): D
}

class NewsMapper @Inject constructor(private val mediaMapper: MediaMapper): Mapper<NewsEntity, NewsModel>{

    companion object{
        const val TYPE_IMAGE = "image"
        const val SUBTYPE_PHOTO = "photo"
    }

    override fun mapToDomain(e: NewsEntity): NewsModel {
        val media = e.media.firstOrNull { it.type == TYPE_IMAGE && it.subtype == SUBTYPE_PHOTO }
        val images = if(media!= null) mediaMapper.mapToDomain(media) else mapOf()
        val newsModel = NewsModel(
            title = e.title,
            summary = e.abstract,
            url = e.articleUrl,
            writer = e.writer,
            publishedDate = e.publishedDate,
            thumbImage = images[MediaMapper.THUMBNAIL],
            articleImage = images[MediaMapper.ARTICLE_PHOTO]
        )
        Timber.d(newsModel.toString())
        return newsModel
    }
}

class MediaMapper @Inject constructor(): Mapper<MediaEntity, Map<String, ImageModel>>{
    companion object{
        const val THUMB = "Standard Thumbnail"
        const val LARGE_THUMB = "thumbLarge"
        const val INLINE_ARTICLE = "Normal"
        const val MEDIUM_3BY2 = "mediumThreeByTwo210"
        const val MEDIUM_3BY2_440 = "mediumThreeByTwo440"

        const val THUMBNAIL = "thumbnail"
        const val ARTICLE_PHOTO = "articlePhoto"
    }
    override fun mapToDomain(e: MediaEntity): Map<String, ImageModel> {
        val images = mutableMapOf<String, ImageModel>()
        var has3by2 = false
        var has3by2440 = false
        e.mediaMetadata.forEach {
            when(it.format){
                THUMB -> {
                    if(!images.containsKey(THUMBNAIL)) images[THUMBNAIL] = ImageModel(
                        it.url, e.caption
                    )
                }
                LARGE_THUMB -> images[THUMBNAIL] = ImageModel(it.url, e.caption)
                INLINE_ARTICLE -> {
                    if(!has3by2 && !has3by2440) images[ARTICLE_PHOTO] = ImageModel(
                        it.url, e.caption
                    )
                }
                MEDIUM_3BY2 -> {
                    if(!has3by2440) {
                        images[ARTICLE_PHOTO] = ImageModel(it.url, e.caption)
                        has3by2 = true
                    }
                }
                MEDIUM_3BY2_440 ->{
                    images[ARTICLE_PHOTO] = ImageModel(it.url, e.caption)
                    has3by2440 = true
                }
            }
        }
        return images

    }
}
