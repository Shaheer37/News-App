package news.app.com.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface NewsDao{
    @Insert
    fun insertNews(news:News)

    @Transaction
    @Query("select * from News")
    fun getNews(): List<News>

    @Query("delete from News")
    fun deleteNews()
}