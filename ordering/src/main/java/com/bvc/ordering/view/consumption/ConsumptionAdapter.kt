package com.bvc.ordering.view.consumption

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bvc.domain.model.ProductEntity
import com.bvc.ordering.databinding.ItemConsumptionBinding

class ConsumptionAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<ProductEntity, ConsumptionAdapter.ViewHolder>(DiffCallback()) {
    interface OnItemClickListener {
        fun onItemClick(item: ProductEntity)

        fun onDeleteClick(item: ProductEntity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemConsumptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), itemClickListener)
    }

    class ViewHolder(
        private val binding: ItemConsumptionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ProductEntity,
            clickListener: OnItemClickListener,
        ) {
            binding.apply {
                this.item = item
                executePendingBindings()

//                tvSmartOrderMaterial.text = item.material.materialName
                clItemConsumptionChange.setOnClickListener {
                    clickListener.onItemClick(item)
                }
                clItemConsumptionDelete.setOnClickListener {
                    clickListener.onDeleteClick(item)
                }

//                val requestOptions = RequestOptions().transform(RoundedCorners(5))
//                Glide
//                    .with(ivSmartThumb.context)
//                    .load(item.material.imageUrl)
//                    .apply(requestOptions)
//                    .into(ivSmartThumb)
//
//                tvQuantity.text = "${item.orderCount}"
//                tvSmartOrderStock.text = "${item.material.unitCount}개"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(
            oldItem: ProductEntity,
            newItem: ProductEntity,
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ProductEntity,
            newItem: ProductEntity,
        ): Boolean = oldItem == newItem
    }
}
