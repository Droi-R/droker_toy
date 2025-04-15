package com.bvc.domain.type

enum class PaymentStatus(
    val desc: String,
) {
    READY("READY"),
    CANCEL("CANCEL"),
    EXPIRED("EXPIRED"),
    REFUND("REFUND"),
    FAILED("FAILED"),
    SUCCESS("SUCCESS"),
    NONE(""),
}
