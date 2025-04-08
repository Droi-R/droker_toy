package com.bvc.domain.type

enum class OrderStatus(
    val desc: String,
) {
    READY("READY"),
    WAITING("WAITING"),
    DONE("DONE"),
    CANCEL("CANCEL"),
}
