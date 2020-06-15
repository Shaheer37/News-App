package news.app.com.ui.news.viewnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_load_state_footer_view_item.*
import news.app.com.R
import news.app.com.ui.utils.toVisibility

class NewsLoadStateAdapter(
        private val retry: () -> Unit
):LoadStateAdapter<NewsLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)

    }

    class LoadStateViewHolder(
            override val containerView: View,
            retry: () -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            retry_button.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                error_msg.text = loadState.error.localizedMessage
            }
            progress_bar.visibility = toVisibility(loadState is LoadState.Loading)
            retry_button.visibility = toVisibility(loadState !is LoadState.Loading)
            error_msg.visibility = toVisibility(loadState !is LoadState.Loading)
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.news_load_state_footer_view_item, parent, false)
                return LoadStateViewHolder(view, retry)
            }
        }
    }
}