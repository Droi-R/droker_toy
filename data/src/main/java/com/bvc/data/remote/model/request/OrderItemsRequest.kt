package com.bvc.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class ProductItemsRequest(
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("main_category_id")
    var mainCategoryId: String,
    @SerializedName("sub_category_id")
    var subCategoryId: String,
    @SerializedName("descriptions")
    val descriptions: String,
    @SerializedName("is_vat")
    var isVat: Boolean,
    @SerializedName("selected")
    val selected: Boolean,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("color")
    var color: String,
    @SerializedName("option_groups")
    val productOption: List<ProductOptionRequest>,
    @SerializedName("position")
    val position: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("base_price")
    val basePrice: String,
    @SerializedName("is_sold_out")
    val isSoldOut: Boolean,
    @SerializedName("is_vat_included")
    val isVatIncluded: Boolean,
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("daily_limit")
    val dailyLimit: Int,
)

data class ProductOptionRequest(
    @SerializedName("option_group_id")
    val optionGroupId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("required")
    val required: Boolean,
    @SerializedName("min_option_count_limit")
    var minOptionCountLimit: Int,
    @SerializedName("max_option_count_limit")
    var maxOptionCountLimit: Int,
    @SerializedName("position")
    var position: Int,
    @SerializedName("options")
    var options: ArrayList<OptionsRequest>,
)

data class StockRequest(
    @SerializedName("external_key")
    val externalKey: String,
    @SerializedName("use_stock")
    var useStock: Boolean,
    @SerializedName("count")
    var count: Int,
)

data class ImagesRequest(
    @SerializedName("id")
    val id: Long,
    @SerializedName("external_key")
    val externalKey: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("is_representative_image")
    var isRepresentativeImage: Boolean,
    @SerializedName("position")
    var position: Int,
)

data class OptionsRequest(
    @SerializedName("product_options_id")
    val id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("use_stock")
    val useStock: Boolean,
    @SerializedName("is_sold_out")
    val isSoldOut: Boolean,
    @SerializedName("is_selected")
    var isSelected: Boolean,
    @SerializedName("materials")
    val materials: List<MaterialsRequest>,
)
