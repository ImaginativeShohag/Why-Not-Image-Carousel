/**
 * Copyright © 2021 Md. Mahmudul Hasan Shohag. All rights reserved.
 */

package org.imaginativeworld.whynotimagecarousel.sample.rough

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.sample.databinding.SimpleRecyclerViewItemBinding

enum class RVItemType {
    WITH_CAROUSEL,
    NO_CAROUSEL,
}

data class RVItem(
    val id: Int,
    val type: RVItemType,
    val list: List<CarouselItem>,
)

class RVItemListAdapter(
    private val lifecycle: Lifecycle,
) : ListAdapter<RVItem, RVItemListAdapter.ListViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<RVItem>() {
                override fun areItemsTheSame(
                    oldItem: RVItem,
                    newItem: RVItem,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: RVItem,
                    newItem: RVItem,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder {
        return ListViewHolder.from(parent, lifecycle)
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ListViewHolder private constructor(
        private val binding: SimpleRecyclerViewItemBinding,
        private val lifecycle: Lifecycle,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RVItem) {
            binding.carousel.registerLifecycle(lifecycle)

            if (item.type == RVItemType.WITH_CAROUSEL) {
                binding.carousel.visibility = View.VISIBLE
                binding.carousel.setData(item.list)

                binding.tv.visibility = View.GONE
            } else {
                binding.carousel.visibility = View.GONE
                binding.carousel.setData(emptyList())

                binding.tv.text = "Current Id: ${item.id}"
                binding.tv.visibility = View.VISIBLE
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                lifecycle: Lifecycle,
            ): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SimpleRecyclerViewItemBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding, lifecycle)
            }
        }
    }
}
