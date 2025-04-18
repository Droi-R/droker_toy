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
        fun onItemClick(item: SmartOrderEntity)
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

                tvMenuName.text = item.material.materialName
                ivCartDelete.setOnClickListener {
                    clickListener.onItemClick(item)
                }

                val requestOptions = RequestOptions().transform(RoundedCorners(5))
                Glide
                    .with(ivMenuThumb.context)
                    .load(item.material.imageUrl)
                    .apply(requestOptions)
                    .into(ivMenuThumb)

                // TODO: 옵션 뷰 동적 생성 필요 시 여기에서 처리
                llCartOption.removeAllViews()
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
