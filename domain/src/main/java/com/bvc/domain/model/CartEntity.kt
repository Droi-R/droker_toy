package com.bvc.domain.model

import com.bvc.domain.type.OrderFrom

data class CartEntity(
    val product: ProductEntity = ProductEntity(),
    val quantity: Int = 1,
    val orderFrom: OrderFrom = OrderFrom.POS,
    val tableNumber: Int = 0,
) {
    fun getTotalPrice(): Int {
        val productPrice = product.price.toInt()
        val selectedOptionsPrice =
            product.productOption
                .filter { it.selected }
                .sumOf { it.price.toInt() }
        return (productPrice + selectedOptionsPrice) * quantity
    }
}
