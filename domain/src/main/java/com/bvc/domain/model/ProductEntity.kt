package com.bvc.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductEntity(
    val productId: String,
    val storeId: String,
    val mainCategoryId: String,
    val subCategoryId: String,
    val name: String,
    val descriptions: String,
    var isVat: Boolean,
    val selected: Boolean,
    val stock: Stock,
    var color: String,
    val useStock: Boolean,
    val optionGroups: List<ProductOptionEntity>,
    val position: Int,
    val quantity: Int,
    val imageUrl: String,
    val basePrice: String,
    val isSoldOut: Boolean,
    val isVatIncluded: Boolean,
    val barcode: String,
    val dailyLimit: Int,
) : Parcelable {
    fun getTotalPrice(): Int {
        val productPrice = basePrice.toInt()
        val selectedOptionsPrice =
            optionGroups
                .flatMap { it.options }
                .filter { it.isSelected }
                .sumOf { it.price.toInt() }
        return (productPrice + selectedOptionsPrice) * quantity
    }
}

@Parcelize
data class ProductOptionEntity(
    val optionGroupId: String,
    val name: String,
    val required: Boolean,
    var minOptionCountLimit: Int,
    var maxOptionCountLimit: Int,
    var position: Int,
    var options: List<Options>,
) : Parcelable

@Parcelize
data class Stock(
    val externalKey: String,
    var useStock: Boolean,
    var count: Int,
) : Parcelable

data class Images(
    val id: Long,
    val externalKey: String,
    var url: String,
    var isRepresentativeImage: Boolean,
    var position: Int,
)

@Parcelize
data class Options(
    val productOptionsId: String,
    var name: String,
    var price: String,
    val position: Int,
    val useStock: Boolean,
    val isSoldOut: Boolean,
    val materials: List<MaterialsEntity>,
    var isSelected: Boolean,
) : Parcelable

data class VatResult(
    val supplyAmount: Int,
    val vat: Int,
    val taxFree: Int,
    val totalAmount: Int,
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
            val supply = (itemTotalPrice * 10) / 11
            val tax = itemTotalPrice - supply
            supplyAmount += supply
            vat += tax
        } else {
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
