package com.bvc.domain.type

enum class PaymentChannel(
    val desc: String,
) {
    OFFLINE("OFFLINE"),
    ONLINE("ONLINE"),
    KAKAO("KAKAO"),
    TOSS("TOSS"),
    NAVER("NAVER"),
    PAYCO("PAYCO"),
    KAKAO_PAY("KAKAO_PAY"),
    ETC("ETC"),
}
