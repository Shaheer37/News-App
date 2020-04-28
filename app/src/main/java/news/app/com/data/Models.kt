package news.app.com.data

import com.google.gson.annotations.SerializedName


data class NewsResponse(
    val status: String = "",
    val results: List<NewsEntity> = emptyList()
)

data class NewsEntity(
    var title: String = "",
    var abstract: String = "",
    @SerializedName("url") var articleUrl: String = "",
    @SerializedName("byline") var writer: String = "",
    @SerializedName("published_date") var publishedDate: String = "",
    var media: List<MediaEntity> = listOf()
)

data class MediaEntity(
    var type: String = "",
    var subtype: String = "",
    var caption: String = "",
    var copyright: String = "",
    @SerializedName("approved_for_syndication")
    var approvedForSyndication: Int,
    @SerializedName("media-metadata")
    var mediaMetadata: List<MediaMetadataEntity> = listOf()
)

data class MediaMetadataEntity(
    var url: String = "",
    var format: String = "",
    var height: Int = 0,
    var width: Int = 0
)