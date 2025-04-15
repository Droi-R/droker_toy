package com.bvc.domain.model

data class CategoryEntity(
    val mainCategoryId: String = "",
    val storeId: String = "",
    val name: String = "",
    val selected: Boolean = false,
)
