package news.app.com.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import news.app.com.ui.utils.SingletonHolder

@Database(entities = [
    News::class
], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object: SingletonHolder<NewsDatabase, Context>(creator = {context ->
        Room.databaseBuilder(context.applicationContext,
                NewsDatabase::class.java, "News.db")
                .build()
    })
}