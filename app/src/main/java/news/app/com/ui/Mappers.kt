package news.app.com.ui

import android.content.Context
import android.os.Build
import news.app.com.domain.models.NewsModel
import news.app.com.domain.models.SourceModel
import news.app.com.ui.injection.components.AppComponent
import news.app.com.ui.models.News
import news.app.com.ui.models.Source
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class SourceMapper @Inject constructor():Mapper<Source, SourceModel>{
    override fun mapToView(d: SourceModel): Source {
        return Source(
            id = d.id,
            name = d.name
        )
    }
}

class NewsMapper(
        private val simpleDateFormat: SimpleDateFormat,
        private val sourceMapper: SourceMapper
):Mapper<News, NewsModel>{

    override fun mapToView(d: NewsModel): News {
        return News(
            title = d.title,
            summary = d.summary,
            articleUrl = d.url,
            image = d.image,
            publishedDate = simpleDateFormat.parse(d.publishedDate)?: Date(),
            writer = d.writer?:"",
            source = d.source?.let { sourceMapper.mapToView(it) }
        )
    }
}

interface Mapper<out V, in D> {
    fun mapToView(d: D): V
}