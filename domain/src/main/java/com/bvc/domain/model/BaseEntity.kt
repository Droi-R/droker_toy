package com.bvc.domain.model

data class Meta(
    val code: Int,
    val message: String,
)

data class Pagination(
    val next: String? = "",
    val previous: String? = "",
    val count: Int? = 0,
    val nextCursor: String? = "",
)

data class Data<T>(
    val data: T,
    val meta: Meta,
)

data class DataList<T>(
    val data: List<T>?,
    val meta: Meta,
    val pagination: Pagination? = Pagination(),
)
