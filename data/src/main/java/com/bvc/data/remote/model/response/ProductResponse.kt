package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("product_id")
    val productId: String? = null,
    @SerializedName("store_id")
    val storeId: String? = null,
    @SerializedName("main_category_id")
    val mainCategoryId: String? = null,
    @SerializedName("sub_category_id")
    val subCategoryId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("descriptions")
    val descriptions: String? = null,
    @SerializedName("is_vat")
    var isVat: Boolean? = null,
    @SerializedName("selected")
    val selected: Boolean? = null,
    @SerializedName("stock")
    val stock: Int? = null,
    @SerializedName("color")
    var color: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("option_groups")
    val optionGroups: List<OptionGroupsResponse>? = null,
    @SerializedName("position")
    val position: Int? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null, // 추가
    @SerializedName("base_price")
    val basePrice: String? = null, // 추가
    @SerializedName("is_sold_out")
    val isSoldOut: Boolean? = null, // 추가
    @SerializedName("is_vat_included")
    val isVatIncluded: Boolean? = null, // 추가
    @SerializedName("barcode")
    val barcode: String? = null, // 추가
    @SerializedName("daily_limit")
    val dailyLimit: Int? = null, // 추가
    @SerializedName("use_stock")
    val useStock: Boolean? = null, // 추가
    @SerializedName("quantity")
    val quantity: Int? = null, // 추가
    @SerializedName("product_recipes")
    val productRecipes: List<RecipeResponse>? = null, // 추가
)

data class OptionGroupsResponse(
    @SerializedName("option_group_id")
    val optionGroupId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("selected")
    val selected: Boolean? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("required")
    val required: Boolean? = null,
    @SerializedName("min_option_count_limit")
    var minOptionCountLimit: Int? = null,
    @SerializedName("max_option_count_limit")
    var maxOptionCountLimit: Int? = null,
    @SerializedName("position")
    var position: Int? = null,
    @SerializedName("options")
    var options: ArrayList<OptionResponse>? = null,
)

data class StockResponse(
    @SerializedName("external_key")
    val externalKey: String? = null,
    @SerializedName("use_stock")
    var useStock: Boolean? = null,
    @SerializedName("count")
    var count: Int? = null,
)

data class ImageResponse(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("external_key")
    val externalKey: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("is_representative_image")
    var isRepresentativeImage: Boolean? = null,
    @SerializedName("position")
    var position: Int? = null,
)

data class OptionResponse(
    @SerializedName("product_options_id")
    val productOptionsId: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("position")
    val position: Int? = null,
    @SerializedName("use_stock")
    val useStock: Boolean? = null,
    @SerializedName("stock")
    val stock: Int? = null,
    @SerializedName("is_sold_out")
    val isSoldOut: Boolean? = null,
    @SerializedName("is_selected")
    var isSelected: Boolean? = null,
    @SerializedName("materials")
    val materials: List<MaterialsResponse>? = null,
    @SerializedName("option_recipes")
    val optionRecipes: List<RecipeResponse>? = null, // 추가
)
