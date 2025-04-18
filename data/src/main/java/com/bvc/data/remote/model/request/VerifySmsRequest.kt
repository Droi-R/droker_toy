package com.bvc.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class VerifySmsRequest(
    @SerializedName("phone_number") var phoneNumber: String,
    @SerializedName("certification_number") val certificationNumber: String,
)
