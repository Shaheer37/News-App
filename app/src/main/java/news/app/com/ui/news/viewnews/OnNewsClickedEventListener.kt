package news.app.com.ui.news.viewnews

import news.app.com.ui.models.News

interface OnNewsClickedEventListener {
    fun onNewsClicked(news: News)
}