package news.app.com

import news.app.com.data.NewsDbMapper
import news.app.com.data.NewsMapper
import news.app.com.data.localdb.NewsLocalDataTest
import news.app.com.data.mapper.NewsDbMapperTest
import news.app.com.data.mapper.NewsMapperTest
import news.app.com.domain.GetNewsUsecaseImpl
import news.app.com.domain.GetNewsUsecaseImplTest
import news.app.com.ui.newsdetails.NewsDetailsViewModelTest
import news.app.com.ui.viewnews.NewsViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        NewsLocalDataTest::class,
        NewsDbMapperTest::class,
        NewsMapperTest::class,
        GetNewsUsecaseImplTest::class,
        news.app.com.ui.mappers.NewsMapperTest::class,
        NewsDetailsViewModelTest::class,
        NewsViewModelTest::class
)
class UnitTestSuite {
}