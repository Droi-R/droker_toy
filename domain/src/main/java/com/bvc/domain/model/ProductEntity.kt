package com.bvc.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductEntity(
    val externalKey: String = "",
    val name: String = "",
    var categoryKey: String = "",
    var categoryName: String = "",
    val descriptions: String = "",
    var isVat: Boolean = true,
    val selected: Boolean = false,
    val stock: Stock = Stock(),
    var color: String = "#ffffff",
//    val images: ArrayList<Images> = ArrayList(),
    val image: String = "",
    val price: String = "",
    val productOption: List<ProductOptionEntity> = emptyList(),
    val position: Int = 0,
    val quantity: Int = 1,
) : Parcelable {
    fun getTotalPrice(): Int {
        val productPrice = price.toInt()
        val selectedOptionsPrice =
            productOption
                .flatMap { it.options }
                .filter { it.isSelected }
                .sumOf { it.price.toInt() }
        return (productPrice + selectedOptionsPrice) * quantity
    }
}

@Parcelize
data class ProductOptionEntity(
    val id: String = "",
    val name: String = "",
//    val selected: Boolean = false,
//    val price: String = "",
    val required: String = "",
    var minOptionCountLimit: Int = 0,
    var maxOptionCountLimit: Int = 0,
    var position: Int = 0,
    var options: ArrayList<Options> = ArrayList<Options>(),
) : Parcelable

@Parcelize
data class Stock(
    val externalKey: String = "",
    var useStock: Boolean = false,
    var count: Int = 0,
) : Parcelable

data class Images(
    val id: Long = 0L,
    val externalKey: String = "",
    var url: String = "",
    var isRepresentativeImage: Boolean = false,
    var position: Int = 0,
)

@Parcelize
data class Options(
    val id: String = "",
    var name: String = "",
    var price: String = "",
    val position: Int = 0,
    val useStock: Boolean = false,
    val stockQuantity: Int = -1,
    val isSoldOut: Boolean = false,
    var isSelected: Boolean = false,
) : Parcelable

data class VatResult(
    val supplyAmount: Int, // 공급가액 (부가세 제외 금액)
    val vat: Int, // 부가세
    val taxFree: Int = 0, // 면세 금액 (부가세 없는 상품 총액)
    val totalAmount: Int, // 총액 (결제금액)
)

fun List<ProductEntity>.calculateVatSummary(): VatResult {
    var supplyAmount = 0
    var vat = 0
    var taxFree = 0
    var totalAmount = 0

    for (product in this) {
        val itemTotalPrice = product.getTotalPrice()
        totalAmount += itemTotalPrice

        if (product.isVat) {
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
