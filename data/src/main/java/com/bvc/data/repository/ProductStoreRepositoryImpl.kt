package com.bvc.data.repository

import com.bvc.domain.log
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.repository.ProductStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductStoreRepositoryImpl
    @Inject
    constructor() : ProductStoreRepository {
        private val productItems = mutableListOf<ProductEntity>()

        override fun addItem(item: ProductEntity) {
            // Check if the item already exists in the cart
            val existingItem =
                productItems.find { cartItem ->
                    cartItem.productId == item.productId &&
                        cartItem.optionGroups
                            .flatMap { it.options }
                            .filter { it.isSelected } ==
                        item.optionGroups
                            .flatMap { it.options }
                            .filter { it.isSelected }
                }
            // If it exists, update the quantity
            log.e("existingItem: $existingItem")
            log.e("item: $item")
            if (existingItem != null) {
                val index = productItems.indexOf(existingItem)
                if (index != -1) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity.plus(1))
                    productItems[index] = updatedItem
                    return
                }
            } else {
                productItems.add(item)
            }
        }

        override fun removeItem(item: ProductEntity) {
            productItems.remove(item)
        }

        override fun minusItem(item: ProductEntity) {
            val existingItem =
                productItems.find { cartItem ->
                    cartItem.productId == item.productId &&
                        cartItem.optionGroups
                            .flatMap { it.options }
                            .filter { it.isSelected } ==
                        item.optionGroups
                            .flatMap { it.options }
                            .filter { it.isSelected }
                }
            // If it exists, update the quantity
            if (existingItem != null) {
                val index = productItems.indexOf(existingItem)
                if (index != -1) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity - 1)
                    if (updatedItem.quantity <= 0) {
                        productItems.removeAt(index)
                    } else {
                        productItems[index] = updatedItem
                    }
                }
            }
        }

        override fun getItems(): List<ProductEntity> = productItems.toList()

        override fun clearCart() {
            productItems.clear()
        }
    }
