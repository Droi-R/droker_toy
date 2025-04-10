package com.bvc.domain.type

enum class OrderStatus(
    val desc: String,
) {
    READY("READY"),
    DONE("DONE"),
    CANCEL("CANCEL"),
}
