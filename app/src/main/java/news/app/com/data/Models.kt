package news.app.com.data

import com.google.gson.annotations.SerializedName


data class NewsResponse(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: List<NewsEntity> = emptyList()
)

data class NewsEntity(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var author: String? = null,
    var url: String = "",
    @SerializedName("publishedAt") var publishedDate: String = "",
    @SerializedName("urlToImage") var imageUrl: String = "",
    var source: SourceEntity? = null
)

data class SourceEntity(
    var id: String? = null,
    var name: String? = null
)