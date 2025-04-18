package com.bvc.domain.repository

import com.bvc.domain.model.ProductEntity

interface ProductStoreRepository {
    fun addItem(item: ProductEntity)

    fun removeItem(item: ProductEntity)

    fun minusItem(item: ProductEntity)

    fun getItems(): List<ProductEntity>

    fun clearCart()
}
