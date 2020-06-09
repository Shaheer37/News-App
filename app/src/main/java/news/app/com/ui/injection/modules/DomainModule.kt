package news.app.com.ui.injection.modules

import androidx.paging.DataSource
import dagger.Binds
import dagger.Module
import news.app.com.domain.GetNewsFromApiUsecase
import news.app.com.domain.GetNewsFromApiUsecaseImpl
import news.app.com.domain.GetNewsUsecase
import news.app.com.domain.GetNewsUsecaseImpl
import news.app.com.domain.models.NewsModel

@Module
interface DomainModule {
    @Binds
    fun bindsGetNewsUsecase(getNewsUsecase: GetNewsUsecaseImpl): GetNewsUsecase

    @Binds
    fun bindGetNewsFromApiUsecase(getNewsFromApiUsecase: GetNewsFromApiUsecaseImpl): GetNewsFromApiUsecase
}