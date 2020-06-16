package news.app.com.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

private const val PREF_NEWS_CACHE_DATE = "newsCacheDate"
private const val PREF_NEWS_IN_RESPONSE = "newsInResponse"

class SessionManager @Inject constructor(private val context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences("news.app.com", MODE_PRIVATE)

    var newsCacheDate: String?
    get() = preferences.getString(PREF_NEWS_CACHE_DATE, null)
    set(value) { preferences.edit().putString(PREF_NEWS_CACHE_DATE, value).apply()}

    var totalNewsInResponse: Int
    get() = preferences.getInt(PREF_NEWS_IN_RESPONSE, 0)
    set(value) { preferences.edit().putInt(PREF_NEWS_IN_RESPONSE, value).apply()}
}