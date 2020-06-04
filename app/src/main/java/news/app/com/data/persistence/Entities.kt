package news.app.com.data.persistence

import androidx.room.*

@Entity
data class News(
        @PrimaryKey(autoGenerate = true) var newsId: Long = 0,
        var title: String = "",
        var description: String = "",
        var url: String = "",
        var author: String = "",
        var publishedDate: String = "",
        var imageUrl: String = "",
        @Embedded var source: Source = Source()
)

data class Source(
        var sourceId: String = "",
        var sourceName: String = ""
)