package com.bvc.data.remote.model

import com.google.gson.annotations.SerializedName

data class AffiliateResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean = false,
    @SerializedName("apiMessage")
    val apiMessage: String = "",
    @SerializedName("name")
    val name: String = "",
)
