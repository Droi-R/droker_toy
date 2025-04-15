package com.bvc.ordering.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bvc.domain.model.ProductEntity
import com.bvc.ordering.R
import com.bvc.ordering.databinding.ItemCartBinding
import com.bvc.ordering.util.Utils

class CartAdapter(
    private var items: List<ProductEntity>,
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: ProductEntity)

        fun minusClick(item: ProductEntity)

        fun plusClick(item: ProductEntity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is DefaultViewHolder -> holder.bind(items[position], itemClickListener)
        }
    }

    fun updateItems(newItems: List<ProductEntity>) {
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
        private val binding: ItemCartBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ProductEntity,
            clickListener: OnItemClickListener,
        ) {
            binding.apply {
                this.item = item
                executePendingBindings()
                ivCartDelete.setOnClickListener {
                    clickListener.onItemClick(item)
                }
                rlCartMinus.setOnClickListener {
                    clickListener.minusClick(item)
                }
                rlCartPlus.setOnClickListener {
                    clickListener.plusClick(item)
                }

                llCartOption.removeAllViews()
                tvMenuName.text = item.name
                tvTotalPrice.text = "${Utils.myFormatter(item.getTotalPrice())}원"
                tvQuantity.text = item.quantity.toString()
                val requestOptions = RequestOptions().transform(RoundedCorners(5))
                Glide
                    .with(ivMenuThumb.context)
                    .load(item.imageUrl)
                    .apply(requestOptions)
                    .into(ivMenuThumb)

                if (item.optionGroups.isNotEmpty()) {
                    llCartOption.visibility = ViewGroup.VISIBLE
                    item.optionGroups.forEach { option ->
                        val context = binding.root.context
                        val optionType = "└ ${if (option.required) {
                            "필수 선택: "
                        } else {
                            "옵션 선택: "
                        }}"
                        option.options.forEach { optionItem ->
                            if (optionItem.price.isNotEmpty() && optionItem.isSelected) {
                                val optionText = "$optionType ${optionItem.name} (${Utils.myFormatter(optionItem.price.toInt())}원)"
                                val textView =
                                    TextView(context).apply {
                                        text = optionText
                                        setTextColor(ContextCompat.getColor(context, R.color.bvc_666E89))
                                        textSize = 13f
                                    }
                                llCartOption.addView(textView)
                            }
                        }
                    }
                } else {
                    llCartOption.visibility = ViewGroup.GONE
                }
            }

            // 옵션 동적 추가
        }
    }
}
