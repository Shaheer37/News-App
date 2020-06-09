package news.app.com.data.persistence

import androidx.room.*

@Entity
data class News(
         @PrimaryKey var title: String = "",
        var description: String? = null,
        var url: String = "",
        var author: String? = null,
        var publishedDate: String? = null,
        var imageUrl: String? = null,
        @Embedded var source: Source? = null
)

data class Source(
        var sourceId: String = "",
        var sourceName: String = ""
)