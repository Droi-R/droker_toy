package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("selected")
    val selected: Boolean = false,
)
