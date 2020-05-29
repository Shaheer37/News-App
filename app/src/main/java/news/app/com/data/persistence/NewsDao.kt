package news.app.com.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface NewsDao{
    @Insert
    fun insertNews(news:News)

    @Insert
    fun insertMedia(media: Media): Long

    @Insert
    fun insertMetadata(metadata: MediaMetadata): Long

    @Transaction
    @Query("select * from News")
    fun getNews(): List<NewsDetails>

    @Transaction
    @Query("select * from Media")
    fun getMedia(): List<MediaDetails>

    @Query("delete from News")
    fun deleteNews()
}