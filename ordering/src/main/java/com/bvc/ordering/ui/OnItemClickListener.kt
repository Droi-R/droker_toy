package com.bvc.ordering.ui

import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.TableEntity

class OnItemClickListener private constructor(
    val onProductClick: ((ProductEntity) -> Unit)?,
    val onTableClick: ((TableEntity) -> Unit)?,
    val onMaterialsClick: ((MaterialsEntity) -> Unit)?,
) {
    fun onProductClick(item: ProductEntity) {
        onProductClick?.invoke(item)
    }

    fun onTableClick(item: TableEntity) {
        onTableClick?.invoke(item)
    }

    fun onMaterialsClick(item: MaterialsEntity) {
        onMaterialsClick?.invoke(item)
    }

    class Builder {
        private var onProductClick: ((ProductEntity) -> Unit)? = null
        private var onTableClick: ((TableEntity) -> Unit)? = null
        private var onMaterialsClick: ((MaterialsEntity) -> Unit)? = null

        fun onProductClick(block: (ProductEntity) -> Unit) {
            onProductClick = block
        }

        fun onTableClick(block: (TableEntity) -> Unit) {
            onTableClick = block
        }

        fun onMaterialsClick(block: (MaterialsEntity) -> Unit) {
            onMaterialsClick = block
        }

        fun build(): OnItemClickListener = OnItemClickListener(onProductClick, onTableClick, onMaterialsClick)
    }
}
