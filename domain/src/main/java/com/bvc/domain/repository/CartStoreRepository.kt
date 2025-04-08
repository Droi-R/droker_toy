package com.bvc.domain.repository

import com.bvc.domain.model.CartEntity

interface CartStoreRepository {
    fun addItem(item: CartEntity)

    fun removeItem(item: CartEntity)

    fun minusItem(item: CartEntity)

    fun getItems(): List<CartEntity>

    fun clearCart()
}
