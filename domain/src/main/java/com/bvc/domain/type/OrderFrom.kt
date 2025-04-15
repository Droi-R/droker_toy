package com.bvc.domain.type

enum class OrderFrom(
    val desc: String,
) {
    POS("셀러"),
    STORE("온라인 스토어"),
    KIOSK("키오스크"),
    DELIVERY("배달"),
    UNKNOWN(""),
    TABLE_ORDER("테이블 오더"),
    FRONT("프론트"),
    TERMINAL("터미널"),
    NONE(""),
}
