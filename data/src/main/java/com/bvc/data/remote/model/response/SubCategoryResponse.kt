package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(
    @SerializedName("sub_category_id")
    val subCategoryId: String = "",
    @SerializedName("store_id")
    val storeId: String = "",
    @SerializedName("main_category_id")
    val mainCategoryId: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("selected")
    val selected: Boolean = false,
)
