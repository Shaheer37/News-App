package news.app.com.data.persistence

import android.net.Uri

object Contract {
    val AUTHORITY = "com.app.news"

    val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

    val PATH_NEWS = "news"

    object NewsEntry{
        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build()
    }
}