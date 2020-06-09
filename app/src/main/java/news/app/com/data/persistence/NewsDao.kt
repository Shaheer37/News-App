package news.app.com.data.persistence

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface NewsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news:News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(news:List<News>)

    @Transaction
    @Query("select * from News")
    fun getNews(): DataSource.Factory<Int, News>
//    fun getNews(): List<News>

    @Query("delete from News")
    fun deleteNews()
}