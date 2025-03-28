package com.bvc.domain.model

data class LoginEntity(
    val isSuccess: Boolean = false,
    val apiMessage: String = "",
    val access: String = "",
    val refresh: String = "",
    val mobilePhoneNumber: String = "",
)
