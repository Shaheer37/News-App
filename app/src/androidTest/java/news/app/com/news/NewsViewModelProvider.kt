package news.app.com.news

import androidx.lifecycle.ViewModel
import news.app.com.domain.GetNewsUsecase
import news.app.com.ui.NewsMapper
import news.app.com.ui.news.viewnews.NewsViewModel
import javax.inject.Provider

class NewsViewModelProvider(
    private val newsMapper: NewsMapper,
    private val getNewsUsecase: GetNewsUsecase
): Provider<ViewModel>{
    override fun get(): ViewModel = NewsViewModel(newsMapper, getNewsUsecase)
}