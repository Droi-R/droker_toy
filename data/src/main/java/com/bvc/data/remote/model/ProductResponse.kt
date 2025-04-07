package com.bvc.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("externalKey")
    val externalKey: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("categoryKey")
    var categoryKey: String = "",
    @SerializedName("categoryName")
    var categoryName: String = "",
    @SerializedName("descriptions")
    val descriptions: String = "",
    @SerializedName("isVat")
    var isVat: Boolean = true,
    @SerializedName("selected")
    val selected: Boolean = false,
    @SerializedName("stock")
    val stock: StockResponse = StockResponse(),
    @SerializedName("color")
    var color: String = "#ffffff",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("productOption")
    val productOption: List<ProductOptionResponse> = emptyList(),
    @SerializedName("position")
    val position: Int = 0,
)

data class ProductOptionResponse(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("selected")
    val selected: Boolean = false,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("required")
    val required: String = "",
    @SerializedName("minOptionCountLimit")
    var minOptionCountLimit: Int = 0,
    @SerializedName("maxOptionCountLimit")
    var maxOptionCountLimit: Int = 0,
    @SerializedName("position")
    var position: Int = 0,
    @SerializedName("options")
    var options: ArrayList<OptionResponse> = ArrayList(),
)

data class StockResponse(
    @SerializedName("externalKey")
    val externalKey: String = "",
    @SerializedName("useStock")
    var useStock: Boolean = false,
    @SerializedName("count")
    var count: Int = 0,
)

data class ImageResponse(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("externalKey")
    val externalKey: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("isRepresentativeImage")
    var isRepresentativeImage: Boolean = false,
    @SerializedName("position")
    var position: Int = 0,
)

data class OptionResponse(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("price")
    var price: String = "",
    @SerializedName("position")
    val position: Int = 0,
    @SerializedName("useStock")
    val useStock: Boolean = false,
    @SerializedName("stockQuantity")
    val stockQuantity: Int = -1,
    @SerializedName("isSoldOut")
    val isSoldOut: Boolean = false,
    @SerializedName("isSelected")
    var isSelected: Boolean = false,
)
