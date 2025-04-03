package com.bvc.ordering.view.inflate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bvc.domain.model.ProductEntity
import com.bvc.ordering.R
import com.bvc.ordering.databinding.ItemGridBinding

class GridAdapter<T : Any>(
    private val itemClickListener: OnItemClickListener<T>,
) : ListAdapter<T, GridAdapter.DefaultViewHolder<T>>(DiffCallback<T>()) {
    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DefaultViewHolder<T> {
        val binding =
            ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DefaultViewHolder<T>,
        position: Int,
    ) {
        holder.bind(getItem(position), itemClickListener)
    }

    class DefaultViewHolder<T>(
        private val binding: ItemGridBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: T,
            clickListener: OnItemClickListener<T>,
        ) {
            binding.apply {
                executePendingBindings()
                root.setOnClickListener {
                    clickListener.onItemClick(item)
                }
                when (item) {
                    is ProductEntity -> {
                        tvGridTitle.text = item.name
                        tvGridDesc.text = item.price
                        Glide
                            .with(
                                ivGrid.context,
                            ).load(
                                item.image,
                            ).centerCrop()
                            .into(ivGrid)
                    }
                }
                clGrid.apply {
                    outlineProvider = ViewOutlineProvider.BACKGROUND
                    clipToOutline = true
                    background = ContextCompat.getDrawable(context, R.drawable.r12_00000000)
                }
            }
        }
    }

    companion object {
        class DiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(
                oldItem: T,
                newItem: T,
            ): Boolean = oldItem == newItem

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: T,
                newItem: T,
            ): Boolean = oldItem == newItem
        }
    }
}
