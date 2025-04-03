package com.bvc.domain.model

data class ProductEntity(
    val id: String = "",
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
)

data class ProductOptionEntity(
    val id: String = "",
    val name: String = "",
    val selected: Boolean = false,
    val price: String = "",
    val required: String = "",
    var minOptionCountLimit: Int = 0,
    var maxOptionCountLimit: Int = 0,
    var position: Int = 0,
    var options: ArrayList<Options> = ArrayList<Options>(),
)

data class Stock(
    val externalKey: String = "",
    var useStock: Boolean = false,
    var count: Int = 0,
)

data class Images(
    val id: Long = 0L,
    val externalKey: String = "",
    var url: String = "",
    var isRepresentativeImage: Boolean = false,
    var position: Int = 0,
)

data class Options(
    val id: String = "",
    var name: String = "",
    var price: String = "",
    val position: Int = 0,
    val useStock: Boolean = false,
    val stockQuantity: Int = -1,
    val isSoldOut: Boolean = false,
    var isSelected: Boolean = false,
)
//    : Parcelable {
//    fun getPriceToText(): String {
//        var subString = ""
//        if (price.isNotEmpty() && price[0].toString() == "-") {
//            subString = price[0].toString()
//        }
//        price = price.replace("-", "")
//        price = "${subString}$price"
//        return if (price.isNotEmpty()) {
//            "${price.toDoubleOrNull()?.toInt()?.let { it1 -> Constant.toPriceFormat(it1) }}원"
//        } else {
//            price
//        }
//    }
// }
