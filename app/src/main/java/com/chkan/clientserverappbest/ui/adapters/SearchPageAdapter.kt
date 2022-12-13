package com.chkan.clientserverappbest.ui.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chkan.clientserverappbest.data.models.Article
import com.chkan.clientserverappbest.databinding.ItemNewsBinding

/**
 * @author Dmytro Chkan on 13.12.2022.
 */
class SearchPageAdapter : PagingDataAdapter<Article, SearchPageViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPageViewHolder =
        SearchPageViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: SearchPageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title && oldItem.url == newItem.url
            }
        }
    }
}

class SearchPageViewHolder(
    private val binding: ItemNewsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article?) {
        with(binding) {
            image.load(article?.urlToImage) {
                placeholder(ColorDrawable(Color.TRANSPARENT))
            }
            title.text = article?.title
        }
    }
}