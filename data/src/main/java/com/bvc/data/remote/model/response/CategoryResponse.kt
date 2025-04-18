package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("main_category_id")
    val mainCategoryId: String? = null,
    @SerializedName("store_id")
    val storeId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("selected")
    val selected: Boolean? = null,
)
