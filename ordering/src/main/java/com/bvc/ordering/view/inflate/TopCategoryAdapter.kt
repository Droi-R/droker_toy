package com.bvc.ordering.view.inflate

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.ordering.R
import com.bvc.ordering.databinding.ItemTopCategoryBinding

class TopCategoryAdapter<T : Any>(
    private val itemClickListener: OnItemClickListener<T>,
) : ListAdapter<T, TopCategoryAdapter.DefaultViewHolder<T>>(DiffCallback<T>()) {
    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DefaultViewHolder<T> {
        val binding =
            ItemTopCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DefaultViewHolder<T>,
        position: Int,
    ) {
        holder.bind(getItem(position), itemClickListener)
    }

    class DefaultViewHolder<T>(
        private val binding: ItemTopCategoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: T,
            clickListener: OnItemClickListener<T>,
        ) {
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
            when (item) {
                is SubCategoryEntity -> {
                    binding.tvSubCategory.text = item.name
                    if (item.selected) {
                        binding.clItemSubCategory.setBackgroundResource(R.drawable.r34_17c2c9)
                        binding.tvSubCategory.setTypeface(null, Typeface.BOLD)
                        binding.tvSubCategory.setTextColor(
                            binding.root.context.getColor(R.color.white),
                        )
                    } else {
                        binding.clItemSubCategory.setBackgroundResource(R.drawable.r34_f6f6f6)
                        binding.tvSubCategory.setTypeface(null, Typeface.NORMAL)
                        binding.tvSubCategory.setTextColor(
                            binding.root.context.getColor(R.color.bvc_666E89),
                        )
                    }
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
