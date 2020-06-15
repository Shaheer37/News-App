package news.app.com.data.persistence

import androidx.paging.DataSource
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface NewsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news:News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(news:List<News>)

    @Query("select COUNT(*) from News")
    fun getNewsCount(): Int

    @Transaction
    @Query("select * from News")
    fun getNewsSource(): PagingSource<Int, News>

    @Transaction
    @Query("select * from News")
    fun getNewsList(): List<News>

    @Query("delete from News")
    suspend fun deleteAllNews(): Int

    @Transaction
    suspend fun wipeOldAndInsertNews(news:List<News>){
        deleteAllNews()
        insertAllNews(news)
    }
}