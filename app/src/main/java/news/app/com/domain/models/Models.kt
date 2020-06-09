package news.app.com.domain.models

import java.util.*
import javax.inject.Inject

data class NewsModel @Inject constructor(
        val title: String,
        val summary: String?,
        val url: String,
        val writer: String?,
        val publishedDate: String?,
        val image: String?,
        val source: SourceModel?
)

data class SourceModel @Inject constructor(
    val id :String,
    val name :String
)