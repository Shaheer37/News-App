package news.app.com.ui.injection.modules

import dagger.Binds
import dagger.Module
import news.app.com.ui.news.viewnews.NewsFragment
import news.app.com.ui.news.viewnews.OnNewsClickedEventListener

@Module
interface NewsFragmentModule {
    @Binds
    fun bindsOnNewsClickedEventListener(onNewsClickedEventListener: NewsFragment): OnNewsClickedEventListener
}