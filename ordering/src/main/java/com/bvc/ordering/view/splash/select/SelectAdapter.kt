package com.bvc.ordering.view.splash.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bvc.domain.model.AffiliateEntity
import com.bvc.ordering.databinding.ItemSelectAlternateBinding
import com.bvc.ordering.databinding.ItemSelectBinding

class SelectAdapter(
    private var items: List<AffiliateEntity>,
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_DEFAULT = 0
        private const val VIEW_TYPE_ALTERNATE = 1
    }

    interface OnItemClickListener {
        fun onItemClick(item: AffiliateEntity)
    }

    override fun getItemViewType(position: Int): Int {
        // 뷰 타입을 결정하는 로직을 추가합니다.
        return if (items[position].type.isEmpty()) VIEW_TYPE_ALTERNATE else VIEW_TYPE_DEFAULT
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_DEFAULT -> {
                val binding = ItemSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DefaultViewHolder(binding)
            }
            VIEW_TYPE_ALTERNATE -> {
                val binding = ItemSelectAlternateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AlternateViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is DefaultViewHolder -> holder.bind(items[position], itemClickListener)
            is AlternateViewHolder -> holder.bind(items[position], itemClickListener)
        }
    }

    fun updateItems(newItems: List<AffiliateEntity>) {
        val diffCallback =
            object : DiffUtil.Callback() {
                override fun getOldListSize() = items.size

                override fun getNewListSize() = newItems.size

                override fun areItemsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int,
                ): Boolean = items[oldItemPosition] == newItems[newItemPosition]

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int,
                ): Boolean = items[oldItemPosition] == newItems[newItemPosition]
            }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    class DefaultViewHolder(
        private val binding: ItemSelectBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: AffiliateEntity,
            clickListener: OnItemClickListener,
        ) {
            binding.item = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
    }

    class AlternateViewHolder(
        private val binding: ItemSelectAlternateBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: AffiliateEntity,
            clickListener: OnItemClickListener,
        ) {
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
    }
}
