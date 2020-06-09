package news.app.com.ui.models

import android.os.Parcelable
import android.text.format.DateUtils
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class News(
    val title: String,
    val summary: String?,
    val articleUrl: String,
    val image: String?,
    val publishedDate: Date?,
    val writer: String?,
    val source: Source?
):Parcelable{
    fun getImageDescription(): String
        = "Image from Article: \"${title}\" published by ${source?.name}"
}

@Parcelize
data class Source(
    val id: String,
    val name: String
):Parcelable