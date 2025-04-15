package com.bvc.ordering.view.inflate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.TableEntity
import com.bvc.ordering.R
import com.bvc.ordering.databinding.ItemGridMaterialsBinding
import com.bvc.ordering.databinding.ItemGridProductBinding
import com.bvc.ordering.databinding.ItemGridTableBinding
import com.bvc.ordering.ui.OnItemClickListener
import com.bvc.ordering.util.Utils

class GridAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<Any, RecyclerView.ViewHolder>(DiffCallback<Any>()) {
    companion object {
        const val VIEW_TYPE_PRODUCT = 0
        const val VIEW_TYPE_TABLE = 1
        const val VIEW_TYPE_MATERIALS = 2

        inline operator fun invoke(block: OnItemClickListener.Builder.() -> Unit): GridAdapter =
            GridAdapter(OnItemClickListener.Builder().apply(block).build())

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_PRODUCT -> {
                val binding =
                    ItemGridProductBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                ProductViewHolder(binding)
            }

            VIEW_TYPE_MATERIALS -> {
                val binding =
                    ItemGridMaterialsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                MaterialsViewHolder(binding)
            }

            VIEW_TYPE_TABLE -> {
                val binding =
                    ItemGridTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TableViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }

    override fun getItemViewType(position: Int): Int {
        when (getItem(position)) {
            is ProductEntity -> {
                return VIEW_TYPE_PRODUCT
            }

            is TableEntity -> {
                return VIEW_TYPE_TABLE
            }

            is MaterialsEntity -> {
                return VIEW_TYPE_MATERIALS
            }

            else -> {
                return VIEW_TYPE_PRODUCT
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        when (holder) {
            is ProductViewHolder -> holder.bind(item, itemClickListener)
            is TableViewHolder -> holder.bind(item, itemClickListener)
            is MaterialsViewHolder -> holder.bind(item, itemClickListener)
            else -> throw IllegalArgumentException("Invalid view holder")
        }
    }

//    override fun onBindViewHolder(
//        holder: ProductViewHolder<T>,
//        position: Int,
//    ) {
//        holder.bind(getItem(position), itemClickListener)
//    }

    class ProductViewHolder(
        private val binding: ItemGridProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(
            item: T,
            clickListener: OnItemClickListener,
        ) {
            binding.apply {
                executePendingBindings()

                when (item) {
                    is ProductEntity -> {
                        root.setOnClickListener {
                            clickListener.onProductClick(item)
                        }
                        tvGridTitle.text = item.name
                        tvGridDesc.text = item.basePrice
                        Glide
                            .with(
                                ivGrid.context,
                            ).load(
                                item.imageUrl,
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

    class TableViewHolder(
        private val binding: ItemGridTableBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(
            item: T,
            clickListener: OnItemClickListener,
        ) {
            binding.apply {
                executePendingBindings()

                when (item) {
                    is TableEntity -> {
                        root.setOnClickListener {
                            clickListener.onTableClick(item)
                        }
                        tvGridTitle.text = item.tableName
                        if (item.orders.isEmpty()) {
                            tvGridTitle.setTextColor(root.context.getColor(R.color.bvc_666E89))
                            clGrid.background =
                                ContextCompat.getDrawable(root.context, R.drawable.r12_f6f6f6)
                            ivGridPlus.isVisible = true
                            tvGridContent.text = ""
                            tvGridBot.text = ""
                        } else {
                            tvGridTitle.setTextColor(root.context.getColor(R.color.white))
                            clGrid.background =
                                ContextCompat.getDrawable(root.context, R.drawable.r12_666e89)
                            ivGridPlus.isVisible = false
                            var contents = ""
                            item.orders.map { order ->
                                order.orderItems.take(3).forEachIndexed { index, product ->
                                    contents +=
                                        if (index < 2 || order.orderItems.size <= 3) {
                                            "${product.name}\n"
                                        } else {
                                            "    ...\n"
                                        }
                                }
                            }
                            tvGridContent.text = contents
                            val price =
                                item.orders.sumOf { order ->
                                    order.orderItems.sumOf { it.getTotalPrice() }
                                }
                            tvGridBot.text = "${Utils.myFormatter(price)}원"
                        }

                        clGrid.apply {
                            outlineProvider = ViewOutlineProvider.BACKGROUND
                            clipToOutline = true
                        }
                    }
                }
            }
        }
    }

    class MaterialsViewHolder(
        private val binding: ItemGridMaterialsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(
            item: T,
            clickListener: OnItemClickListener,
        ) {
            binding.apply {
                executePendingBindings()

                when (item) {
                    is MaterialsEntity -> {
                        root.setOnClickListener {
                            clickListener.onMaterialsClick(item)
                        }
                        tvGridMaterialTitle.text = item.materialName
                        circularDonutView.progress =
                            item.stock.toFloat() / item.safetyStock.toFloat()
                        tvGridMaterialCount.text =
                            "${item.stock} / ${item.safetyStock}"
                        clGrid.apply {
                            background =
                                ContextCompat.getDrawable(root.context, R.drawable.r12_f6f6f6)
                            outlineProvider = ViewOutlineProvider.BACKGROUND
                            clipToOutline = true
                        }
                    }
                }
            }
        }
    }
}
