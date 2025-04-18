package com.bvc.ordering.ui.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class Event<out T>(
    private val content: T,
) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
}

// StateFlow.collect는 LifecycleScope.launch 블록 안에서 사용해야 함
// (코루틴이 lifecycle 상태에 맞춰 자동으로 캔슬되도록 하기 위함)
// collect는 lifecycleScope.launch 안에서 호출해야 안전하게 lifecycle에 맞춰 처리됨
fun <T> StateFlow<List<T>>.collectNonEmpty(
    lifecycleOwner: LifecycleOwner,
    collector: suspend (List<T>) -> Unit,
) {
    lifecycleOwner.lifecycleScope.launch {
        this@collectNonEmpty
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .collect { collector(it) }
    }
}
