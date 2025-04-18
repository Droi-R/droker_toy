package com.bvc.domain.model

data class SubCategoryEntity(
    val subCategoryId: String = "",
    val storeId: String = "",
    val mainCategoryId: String = "",
    val name: String = "",
    val selected: Boolean = false,
)
