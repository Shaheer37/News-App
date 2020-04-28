package news.app.com.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val title: String,
    val summary: String,
    val articleUrl: String,
    val thumbImage: Image? = null,
    val articleImage: Image? = null
):Parcelable

@Parcelize
data class Image(
    val url: String,
    val caption: String
):Parcelable