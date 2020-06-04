package news.app.com.data

import news.app.com.domain.models.NewsModel
import news.app.com.domain.models.SourceModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import javax.inject.Inject

interface Mapper<in E, out D> {
    fun mapToDomain(e: E): D
}

class NewsMapper @Inject constructor(private val sourceMapper: SourceMapper): Mapper<NewsEntity, NewsModel>{

    override fun mapToDomain(e: NewsEntity): NewsModel {
        Timber.d(e.toString())
        return NewsModel(
                title = e.title,
                summary = e.description,
                url = e.url,
                writer = e.author,
                publishedDate = e.publishedDate,
                image = e.imageUrl,
                source = e.source?.let { sourceMapper.mapToDomain(it) }
        )
    }
}

class SourceMapper @Inject constructor(): Mapper<SourceEntity, SourceModel?>{

    override fun mapToDomain(e: SourceEntity): SourceModel? {
        if(e.id == null || e.name == null) return null

        return SourceModel(
                id = e.id!!,
                name = e.name!!
        )
    }
}
