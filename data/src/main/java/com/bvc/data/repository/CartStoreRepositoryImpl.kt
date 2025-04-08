package com.bvc.data.repository

import com.bvc.domain.model.CartEntity
import com.bvc.domain.repository.CartStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartStoreRepositoryImpl
    @Inject
    constructor() : CartStoreRepository {
        private val cartItems = mutableListOf<CartEntity>()

        override fun addItem(item: CartEntity) {
            // Check if the item already exists in the cart
            val existingItem =
                cartItems.find { cartItem ->
                    cartItem.product.externalKey == item.product.externalKey &&
                        cartItem.product.productOption
                            .flatMap { it.options }
                            .filter { it.isSelected } ==
                        item.product.productOption
                            .flatMap { it.options }
                            .filter { it.isSelected }
                }
            // If it exists, update the quantity
            if (existingItem != null) {
                val index = cartItems.indexOf(existingItem)
                if (index != -1) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + item.quantity)
                    cartItems[index] = updatedItem
                    return
                }
            } else {
                cartItems.add(item)
            }
        }

        override fun removeItem(item: CartEntity) {
            cartItems.remove(item)
        }

        override fun minusItem(item: CartEntity) {
            val existingItem =
                cartItems.find { cartItem ->
                    cartItem.product.externalKey == item.product.externalKey &&
                        cartItem.product.productOption
                            .flatMap { it.options }
                            .filter { it.isSelected } ==
                        item.product.productOption
                            .flatMap { it.options }
                            .filter { it.isSelected }
                }
            // If it exists, update the quantity
            if (existingItem != null) {
                val index = cartItems.indexOf(existingItem)
                if (index != -1) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity - 1)
                    if (updatedItem.quantity <= 0) {
                        cartItems.removeAt(index)
                    } else {
                        cartItems[index] = updatedItem
                    }
                }
            }
        }

        override fun getItems(): List<CartEntity> = cartItems.toList()

        override fun clearCart() {
            cartItems.clear()
        }
    }
