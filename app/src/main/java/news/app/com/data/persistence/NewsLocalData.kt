package news.app.com.data.persistence

import androidx.paging.PagingSource
import androidx.room.withTransaction
import javax.inject.Inject

class NewsLocalData @Inject constructor(private val newsDatabase: NewsDatabase) {
    fun getNews(): PagingSource<Int, News> = newsDatabase.newsDao().getNewsSource()

    suspend fun getNewsCount(): Int = newsDatabase.newsDao().getNewsCount()

    suspend fun insertNews(news: List<News>) = newsDatabase.newsDao().insertAllNews(news)

    suspend fun insertNewsKeys(newsKeys: List<RemoteKeys>) = newsDatabase.remoteKeysDao().insertAll(newsKeys)

    suspend fun getNewsKeyForNewsTitle(newsTitle: String): RemoteKeys? = newsDatabase.remoteKeysDao().remoteKeysNewsId(newsTitle)

    suspend fun clearDatabase(){
        newsDatabase.remoteKeysDao().clearRemoteKeys()
        newsDatabase.newsDao().deleteAllNews()
    }

}