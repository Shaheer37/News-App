package news.app.com.news

import news.app.com.ui.injection.ViewModelFactory
import news.app.com.ui.news.viewnews.NewsAdapter
import news.app.com.ui.news.viewnews.NewsFragment

class NewsFragmentTestWrapper: NewsFragment(){

    companion object{
        lateinit var newsViewModelFactory: ViewModelFactory
    }

    override fun injectDependencies() {
        adapter = NewsAdapter(this)
        viewModelFactory = newsViewModelFactory
    }
}