package com.bvc.domain.model

data class Store(
    val storeId: Int = -1,
    val ownerID: Int = -1,
    val tid: String = "",
    val name: String = "",
    val address: String = "",
    val isActive: Int = -1,
)
