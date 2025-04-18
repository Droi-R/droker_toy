package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    @SerializedName("user")
    val user: UserResponse? = null,
    @SerializedName("mobilePhoneNumber")
    val mobilePhoneNumber: String? = null,
)

data class UserResponse(
    @SerializedName("user_id")
    val userId: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
)
