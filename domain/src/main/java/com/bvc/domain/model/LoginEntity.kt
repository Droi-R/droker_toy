package com.bvc.domain.model

data class LoginEntity(
    val accessToken: String = "",
    val refreshToken: String = "",
    val user: UserEntity = UserEntity(),
    val mobilePhoneNumber: String = "",
)

data class UserEntity(
    val userId: String = "",
    val phone: String = "",
    val role: String = "",
    val createdAt: String = "",
)
