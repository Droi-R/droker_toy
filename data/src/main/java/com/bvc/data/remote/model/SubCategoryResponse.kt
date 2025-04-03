package com.bvc.data.remote.model

import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("selected")
    val selected: Boolean = false,
)
