package news.app.com.data.persistence

import androidx.room.*

@Entity
data class News(
        @PrimaryKey var newsId: String = "",
        var title: String = "",
        var subtitle: String = "",
        var articleUrl: String = "",
        var writer: String = "",
        var publishedDate: String = ""
)

@Entity(foreignKeys = [
    ForeignKey(
            entity = News::class,
            parentColumns = ["newsId"],
            childColumns = ["newsId"],
            onDelete = ForeignKey.CASCADE)
])
data class Media(
        @PrimaryKey(autoGenerate = true) var mediaId: Long = 0,
        var newsId: String = "",
        var type: String = "",
        var subtype: String = "",
        var caption: String = "",
        var copyright: String = "",
        var approvedFroSyndication: Int = 0
)

@Entity(foreignKeys = [
    ForeignKey(
            entity = Media::class,
            parentColumns = ["mediaId"],
            childColumns = ["mediaId"],
            onDelete = ForeignKey.CASCADE)
])
data class MediaMetadata(
        @PrimaryKey(autoGenerate = true) var metadataId: Long = 0,
        var mediaId: Long,
        var url: String = "",
        var format: String = "",
        var height: Int = 0,
        var width: Int = 0
)


data class NewsDetails(
        @Embedded val news: News = News(),
        @Relation(
                parentColumn = "newsId",
                entityColumn = "newsId",
                entity = Media::class
        )
        val media: List<MediaDetails> = listOf()
)

data class MediaDetails(
        @Embedded val media: Media = Media(),
        @Relation(
                parentColumn = "mediaId",
                entityColumn = "mediaId",
                entity = MediaMetadata::class
        )
        val metaData: List<MediaMetadata> = listOf()
)