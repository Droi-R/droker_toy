package com.bvc.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String = "",
    @SerializedName("refresh_token")
    val refreshToken: String = "",
    @SerializedName("user")
    val user: UserResponse = UserResponse(),
    @SerializedName("mobilePhoneNumber")
    val mobilePhoneNumber: String = "",
)

data class UserResponse(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("role")
    val role: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
)
