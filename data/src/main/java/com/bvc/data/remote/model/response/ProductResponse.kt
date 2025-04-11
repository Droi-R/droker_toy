package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("externalKey")
    val externalKey: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("categoryKey")
    var categoryKey: String? = null,
    @SerializedName("categoryName")
    var categoryName: String? = null,
    @SerializedName("descriptions")
    val descriptions: String? = null,
    @SerializedName("isVat")
    var isVat: Boolean? = null,
    @SerializedName("selected")
    val selected: Boolean? = null,
    @SerializedName("stock")
    val stock: StockResponse? = null,
    @SerializedName("color")
    var color: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("productOption")
    val productOption: List<ProductOptionResponse>? = null,
    @SerializedName("position")
    val position: Int? = null,
)

data class ProductOptionResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("selected")
    val selected: Boolean? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("required")
    val required: String? = null,
    @SerializedName("minOptionCountLimit")
    var minOptionCountLimit: Int? = null,
    @SerializedName("maxOptionCountLimit")
    var maxOptionCountLimit: Int? = null,
    @SerializedName("position")
    var position: Int? = null,
    @SerializedName("options")
    var options: ArrayList<OptionResponse>? = null,
)

data class StockResponse(
    @SerializedName("externalKey")
    val externalKey: String? = null,
    @SerializedName("useStock")
    var useStock: Boolean? = null,
    @SerializedName("count")
    var count: Int? = null,
)

data class ImageResponse(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("externalKey")
    val externalKey: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("isRepresentativeImage")
    var isRepresentativeImage: Boolean? = null,
    @SerializedName("position")
    var position: Int? = null,
)

data class OptionResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("position")
    val position: Int? = null,
    @SerializedName("useStock")
    val useStock: Boolean? = null,
    @SerializedName("stockQuantity")
    val stockQuantity: Int? = null,
    @SerializedName("isSoldOut")
    val isSoldOut: Boolean? = null,
    @SerializedName("isSelected")
    var isSelected: Boolean? = null,
)
