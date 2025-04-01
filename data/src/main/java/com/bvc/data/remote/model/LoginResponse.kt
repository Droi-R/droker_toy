package com.bvc.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access")
    val access: String = "",
    @SerializedName("refresh")
    val refresh: String = "",
    @SerializedName("mobilePhoneNumber")
    val mobilePhoneNumber: String = "",
)
