package news.app.com.ui.injection.modules

import dagger.Binds
import dagger.Module
import news.app.com.domain.GetNewsUsecase
import news.app.com.domain.GetNewsUsecaseImpl

@Module
interface DomainModule {
    @Binds
    fun bindsGetNewsUsecase(getNewsUsecase: GetNewsUsecaseImpl): GetNewsUsecase
}