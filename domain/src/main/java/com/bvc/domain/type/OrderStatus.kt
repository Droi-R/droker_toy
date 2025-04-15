package com.bvc.domain.type

enum class OrderStatus(
    val desc: String,
) {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    COOKING("COOKING"),
    COMPLETED("COMPLETED"),
    NONE(""),
}
