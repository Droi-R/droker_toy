package com.bvc.domain.model

data class Store(
    val storeId: Int = -1,
    val ownerID: Int = -1,
    val cats: List<Cats> = emptyList(),
    val name: String = "",
    val address: String = "",
    val isActive: Boolean = false,
)

data class Cats(
    val catId: String = "",
)
