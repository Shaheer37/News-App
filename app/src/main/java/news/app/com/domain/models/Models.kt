package news.app.com.domain.models

import javax.inject.Inject

data class NewsModel @Inject constructor(
    val title: String,
    val summary: String,
    val url: String,
    val writer: String,
    val publishedDate: String,
    val thumbImage: ImageModel?,
    val articleImage: ImageModel?
)

data class ImageModel @Inject constructor(
    val url :String,
    val caption :String
)