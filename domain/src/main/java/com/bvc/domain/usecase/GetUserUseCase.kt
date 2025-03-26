package com.bvc.domain.usecase

import com.bvc.domain.model.YoEntity
import com.bvc.domain.repository.YoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: YoRepository,
) {
    operator fun invoke(
        q: String,
        scope: CoroutineScope,
        onResult: (YoEntity.Res) -> Unit = {},
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                repository.getUsers(q)
            }
            onResult(deferred.await())
        }
    }
}
