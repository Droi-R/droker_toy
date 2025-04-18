package com.bvc.domain.utils

import com.bvc.domain.type.ApiStatus

object Constant {
    const val BASE_URL = "http://175.45.200.125:3000"

    object APICODE {
        const val SUCCESS = "200"
        const val SUCCESS_DEV = "201"
        const val NOT_FOUND = "205"
        const val UNAUTHORIZED = "401"
        const val EXPIRED_TOKEN = "403"
        const val INTERNAL_ERROR = "500"
    }

    fun getStatus(code_int: Int): ApiStatus {
        val code = code_int.toString()
        val successCode =
            arrayOf(
                APICODE.SUCCESS,
                APICODE.SUCCESS_DEV,
            )

        return if (successCode.any { it.startsWith(code) }) {
            ApiStatus.SUCCESS
        } else if (code == APICODE.UNAUTHORIZED) {
            ApiStatus.UNAUTHORIZED
        } else if (code == APICODE.NOT_FOUND) {
            ApiStatus.NOT_FOUND
        } else if (code == APICODE.EXPIRED_TOKEN) {
            ApiStatus.EXPIRED_TOKEN
        } else if (code == APICODE.INTERNAL_ERROR) {
            ApiStatus.INTERNAL_ERROR
        } else {
            ApiStatus.ERROR
        }
    }

    object PREF {
        const val BVC_TOKEN = "bvc_token"
        const val BVC_REFRESH_TOKEN = "bvc_refresh_token"
        const val BVC_USER_ID = "bvc_user_id"
        const val BVC_STORE_NAME = "bvc_store_name"
        const val BVC_STORE_TYPE = "bvc_store_type"
        const val BVC_STORE_ID = "bvc_store_id"
    }
}
