package com.bvc.ordering.view.materials

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bvc.domain.model.SmartOrderEntity
import com.bvc.ordering.databinding.ItemSmartOrderBinding

class SmartOrderAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<SmartOrderEntity, SmartOrderAdapter.ViewHolder>(DiffCallback()) {
    interface OnItemClickListener {
        fun onItemDeleteClick(item: SmartOrderEntity)

        fun onItemChangeClick(item: SmartOrderEntity)

        fun onItemPlusClick(item: SmartOrderEntity)

        fun onItemMinusClick(item: SmartOrderEntity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemSmartOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), itemClickListener)
    }

    class ViewHolder(
        private val binding: ItemSmartOrderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: SmartOrderEntity,
            clickListener: OnItemClickListener,
        ) {
            binding.apply {
                this.item = item
                executePendingBindings()

                tvSmartOrderMaterial.text = item.material.materialName
                clItemSmartOrderDelete.setOnClickListener {
                    clickListener.onItemDeleteClick(item)
                }
                clItemSmartOrderStockChange.setOnClickListener {
                    clickListener.onItemChangeClick(item)
                }
                rlSmartOrderPlus.setOnClickListener {
                    clickListener.onItemPlusClick(item)
                }
                rlSmartOrderMinus.setOnClickListener {
                    clickListener.onItemMinusClick(item)
                }

                val requestOptions = RequestOptions().transform(RoundedCorners(5))
                Glide
                    .with(ivSmartThumb.context)
                    .load(item.material.imageUrl)
                    .apply(requestOptions)
                    .into(ivSmartThumb)

                tvQuantity.text = "${item.orderCount}"
                tvSmartOrderStock.text = "${item.material.unitCount}개"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SmartOrderEntity>() {
        override fun areItemsTheSame(
            oldItem: SmartOrderEntity,
            newItem: SmartOrderEntity,
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: SmartOrderEntity,
            newItem: SmartOrderEntity,
        ): Boolean = oldItem == newItem
    }
}
