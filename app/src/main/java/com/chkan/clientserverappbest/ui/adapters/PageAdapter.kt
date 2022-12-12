package com.chkan.clientserverappbest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chkan.clientserverappbest.databinding.PageViewholderBinding
import com.chkan.clientserverappbest.domain.ModelDomain

/**
 * @author Dmytro Chkan on 09.12.2022.
 */
class PageAdapter : PagingDataAdapter<ModelDomain, PageViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder =
        PageViewHolder(
            PageViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {//can get null for placeholder
            holder.bind(item)
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ModelDomain>() {
            override fun areItemsTheSame(oldItem: ModelDomain, newItem: ModelDomain): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ModelDomain, newItem: ModelDomain): Boolean =
                oldItem == newItem
        }
    }
}

class PageViewHolder(
    private val binding: PageViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ModelDomain) {
        binding.apply {
            binding.textName.text = item.firstName
            binding.textSecond.text = item.lastName
            binding.logoCompany.load(item.picture)
        }
    }
}