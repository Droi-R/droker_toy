package com.bvc.domain.utils

import com.bvc.domain.type.ErrorType

interface RemoteErrorEmitter {
    fun onError(msg: String)

    fun onError(errorType: ErrorType)
}
