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
                .flatMap { it.options }
                .filter { it.isSelected }
                .sumOf { it.price.toInt() }
        return (productPrice + selectedOptionsPrice) * quantity
    }
}

data class VatResult(
    val supplyAmount: Int, // 공급가액 (부가세 제외 금액)
    val vat: Int, // 부가세
    val taxFree: Int = 0, // 면세 금액 (부가세 없는 상품 총액)
    val totalAmount: Int, // 총액 (결제금액)
)

fun List<CartEntity>.calculateVatSummary(): VatResult {
    var supplyAmount = 0
    var vat = 0
    var taxFree = 0
    var totalAmount = 0

    for (cart in this) {
        val itemTotalPrice = cart.getTotalPrice()
        totalAmount += itemTotalPrice

        if (cart.product.isVat) {
            // 부가세 포함 → 공급가액과 부가세 분리
            val supply = (itemTotalPrice * 10) / 11
            val tax = itemTotalPrice - supply
            supplyAmount += supply
            vat += tax
        } else {
            // 면세 상품 → 전부 taxFree
            taxFree += itemTotalPrice
        }
    }

    return VatResult(
        supplyAmount = supplyAmount,
        vat = vat,
        taxFree = taxFree,
        totalAmount = totalAmount,
    )
}
