package com.bvc.data.repository

import com.bvc.domain.model.ProductEntity
import com.bvc.domain.repository.TableStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableStoreRepositoryImpl
    @Inject
    constructor() : TableStoreRepository {
        private val productItems = mutableListOf<ProductEntity>()

        override fun addItem(item: ProductEntity) {
            // Check if the item already exists in the cart
            val existingItem =
                productItems.find { cartItem ->
                    cartItem.externalKey == item.externalKey &&
                        cartItem.productOption
                            .flatMap { it.options }
                            .filter { it.isSelected } ==
                        item.productOption
                            .flatMap { it.options }
                            .filter { it.isSelected }
                }
            // If it exists, update the quantity
            if (existingItem != null) {
                val index = productItems.indexOf(existingItem)
                if (index != -1) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + item.quantity)
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
                    cartItem.externalKey == item.externalKey &&
                        cartItem.productOption
                            .flatMap { it.options }
                            .filter { it.isSelected } ==
                        item.productOption
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
