package news.app.com.data

import news.app.com.data.persistence.News
import news.app.com.data.persistence.Source
import news.app.com.domain.models.NewsModel
import news.app.com.domain.models.SourceModel
import timber.log.Timber
import javax.inject.Inject

interface Mapper<in E, out D> {
    fun map(e: E): D
}

class NewsDbMapper @Inject constructor(private val sourceMapper: SourceDbMapper): Mapper<NewsEntity, News>{

    override fun map(e: NewsEntity): News {
        Timber.d(e.toString())
        return News(
                title = e.title,
                description = e.description,
                url = e.url,
                author = e.author?:"",
                publishedDate = e.publishedDate,
                imageUrl = e.imageUrl,
                source = e.source?.let { sourceMapper.map(it) }
        )
    }
}

class SourceDbMapper @Inject constructor(): Mapper<SourceEntity, Source?>{

    override fun map(e: SourceEntity): Source? {
        if(e.id == null || e.name == null) return null

        return Source(
                sourceId = e.id!!,
                sourceName = e.name!!
        )
    }
}

class NewsMapper @Inject constructor(private val sourceMapper: SourceMapper): Mapper<News, NewsModel>{

    override fun map(e: News): NewsModel {
        Timber.d(e.toString())
        return NewsModel(
                title = e.title,
                summary = e.description,
                url = e.url,
                writer = e.author,
                publishedDate = e.publishedDate,
                image = e.imageUrl,
                source = e.source?.let { sourceMapper.map(it) }
        )
    }
}

class SourceMapper @Inject constructor(): Mapper<Source, SourceModel>{

    override fun map(e: Source): SourceModel {

        return SourceModel(
                id = e.sourceId,
                name = e.sourceName
        )
    }
}
