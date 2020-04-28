package news.app.com.ui

import news.app.com.domain.models.ImageModel
import news.app.com.domain.models.NewsModel
import news.app.com.ui.models.Image
import news.app.com.ui.models.News
import javax.inject.Inject


class ImageMapper @Inject constructor():Mapper<Image, ImageModel>{
    override fun mapToView(d: ImageModel): Image {
        return Image(
            url = d.url,
            caption = d.caption
        )
    }
}

class NewsMapper @Inject constructor(private val imageMapper: ImageMapper):Mapper<News, NewsModel>{
    override fun mapToView(d: NewsModel): News {
        return News(
            title = d.title,
            summary = d.summary,
            articleUrl = d.url,
            thumbImage = d.thumbImage?.let { imageMapper.mapToView(it) },
            articleImage = d.articleImage?.let { imageMapper.mapToView(it) }
        )
    }
}

interface Mapper<out V, in D> {
    fun mapToView(d: D): V
}