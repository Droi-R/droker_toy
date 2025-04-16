package com.bvc.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class ProductItemsRequest(
    @SerializedName("product_id")
    val productId: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("category_key")
    var categoryKey: String = "",
    @SerializedName("category_name")
    var categoryName: String = "",
    @SerializedName("descriptions")
    val descriptions: String = "",
    @SerializedName("is_vat")
    var isVat: Boolean = true,
    @SerializedName("selected")
    val selected: Boolean = false,
    @SerializedName("stock")
    val stock: StockRequest = StockRequest(),
    @SerializedName("color")
    var color: String = "#ffffff",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("option_groups")
    val productOption: List<ProductOptionRequest> = emptyList(),
    @SerializedName("position")
    val position: Int = 0,
    @SerializedName("quantity")
    val quantity: Int = 0,
    @SerializedName("image_url")
    val imageUrl: String = "", // 추가
    @SerializedName("base_price")
    val basePrice: String = "", // 추가
    @SerializedName("is_sold_out")
    val isSoldOut: Boolean = false, // 추가
    @SerializedName("is_vat_included")
    val isVatIncluded: Boolean = true, // 추가
    @SerializedName("barcode")
    val barcode: String = "", // 추가
    @SerializedName("daily_limit")
    val dailyLimit: Int = 0, // 추가
)

data class ProductOptionRequest(
    @SerializedName("option_group_id")
    val optionGroupId: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("required")
    val required: Boolean = false,
    @SerializedName("min_option_count_limit")
    var minOptionCountLimit: Int = 0,
    @SerializedName("max_option_count_limit")
    var maxOptionCountLimit: Int = 0,
    @SerializedName("position")
    var position: Int = 0,
    @SerializedName("options")
    var options: ArrayList<OptionsRequest> = ArrayList(),
)

data class StockRequest(
    @SerializedName("external_key")
    val externalKey: String = "",
    @SerializedName("use_stock")
    var useStock: Boolean = false,
    @SerializedName("count")
    var count: Int = 0,
)

data class ImagesRequest(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("external_key")
    val externalKey: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("is_representative_image")
    var isRepresentativeImage: Boolean = false,
    @SerializedName("position")
    var position: Int = 0,
)

data class OptionsRequest(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("price")
    var price: String = "",
    @SerializedName("position")
    val position: Int = 0,
    @SerializedName("use_stock")
    val useStock: Boolean = false,
    @SerializedName("is_sold_out")
    val isSoldOut: Boolean = false,
    @SerializedName("is_selected")
    var isSelected: Boolean = false,
)
