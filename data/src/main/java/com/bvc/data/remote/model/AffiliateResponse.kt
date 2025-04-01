package com.bvc.data.remote.model

import com.google.gson.annotations.SerializedName

data class AffiliateResponse(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: String = "",
)
