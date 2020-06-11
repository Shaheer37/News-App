package news.app.com.ui

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.InvalidationTracker
import androidx.room.RoomSQLiteQuery
import androidx.room.paging.LimitOffsetDataSource
import news.app.com.data.persistence.NewsDatabase
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

private val mockQuery = mock(RoomSQLiteQuery::class.java)
        .apply { `when`(sql).thenReturn("") }


private val newsDatabase = mock(NewsDatabase::class.java)
        .apply { `when`(invalidationTracker).thenReturn(mock(InvalidationTracker::class.java)) }

fun <T> createMockDataSourceFactory(itemList: List<T>): DataSource.Factory<Int, T> =
        object : DataSource.Factory<Int, T>() {
            override fun create(): DataSource<Int, T>{
                return MockLimitDataSource(itemList)
            }
        }

class MockLimitDataSource<T>(private val itemList: List<T>) : LimitOffsetDataSource<T>(newsDatabase, mockQuery, false, null) {

    override fun convertRows(cursor: Cursor?): MutableList<T> = itemList.toMutableList()
    override fun countItems(): Int = itemList.count()
    override fun isInvalid(): Boolean = false
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
//        callback.onResult(itemList.toMutableList())
    }

    override fun loadRange(startPosition: Int, loadCount: Int): MutableList<T> {
        return itemList.subList(startPosition, startPosition + loadCount).toMutableList()
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        callback.onResult(itemList, 0, itemList.size)
    }

}

fun <T> List<T>.asPagedList(config: PagedList.Config? = null): PagedList<T>? {
    val defaultConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(size)
            .setMaxSize(size + 2)
            .setPrefetchDistance(1)
            .build()
    return LivePagedListBuilder<Int, T>(
            createMockDataSourceFactory(this),
            config ?: defaultConfig
    ).build().blockingObserve()
}

fun <T> LiveData<T>.blockingObserve(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)

    val observer = Observer<T> { t ->
        value = t
        latch.countDown()
    }

    observeForever(observer)

    latch.await(2, TimeUnit.SECONDS)
    return value
}
