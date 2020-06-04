package news.app.com.ui.news.viewnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_news.*
import news.app.com.R
import news.app.com.ui.models.News
import javax.inject.Inject

class NewsAdapter @Inject constructor(private val onNewsClickedEventListener: OnNewsClickedEventListener): ListAdapter<News, NewsAdapter.NewsHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_news, parent, false)
        return NewsHolder(view)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind()
    }

    inner class NewsHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(){
            val news = getItem(adapterPosition)
            news_title.text = news.title
            news_image.setImageURI(news.image)
            news_image.contentDescription = news.getImageDescription()

            containerView.setOnClickListener { onNewsClickedEventListener.onNewsClicked(news) }
        }
    }
}

class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.summary == newItem.summary &&
            oldItem.articleUrl == newItem.articleUrl &&
            oldItem.image == newItem.image &&
            oldItem.writer == newItem.writer &&
            oldItem.publishedDate.compareTo(newItem.publishedDate) == 0 &&
            oldItem.source?.id == newItem.source?.id &&
            oldItem.source?.name == newItem.source?.name
    }
}