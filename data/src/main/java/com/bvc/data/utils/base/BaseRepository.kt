package com.bvc.data.utils.base

import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.ResMeta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {
    companion object {
        private const val TAG = "BaseRemoteRepository"
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    /**
     * Function that executes the given function on Dispatchers.IO context and switch to Dispatchers.Main context when an error occurs
     * @param callFunction is the function that is returning the wanted object. It must be a suspend function. Eg:
     * override suspend fun loginUser(body: LoginUserBody, emitter: RemoteErrorEmitter): LoginUserResponse?  = safeApiCall( { authApi.loginUser(body)} , emitter)
     * @param emitter is the interface that handles the error messages. The error messages must be displayed on the MainThread, or else they would throw an Exception.
     */

    suspend inline fun <reified T> safeApiCall(crossinline callFunction: suspend () -> Response<T>): Response<T> =
        try {
            val response = withContext(Dispatchers.IO) { callFunction.invoke() }

            if (response.isSuccessful) {
                response
            } else {
                val message = response.errorBody()?.string() ?: "알 수 없는 오류"
                val fakeBody = createFakeErrorBody<T>(response.code(), message)
                Response.success(fakeBody)
            }
        } catch (e: Exception) {
            val fakeBody = createFakeErrorBody<T>(-1, e.localizedMessage ?: "Unknown error")
            Response.success(fakeBody)
        }

    inline fun <reified T> createFakeErrorBody(
        code: Int,
        message: String,
    ): T {
        val meta = ResMeta(code = code, message = message)

        return when (T::class) {
            ResData::class -> ResData<Any>(data = null, meta = meta) as T
            ResDataList::class -> ResDataList<Any>(data = emptyList(), meta = meta) as T
            else -> throw IllegalArgumentException("Unsupported response wrapper: ${T::class}")
        }
    }

    /**
     * Function that executes the given function in whichever thread is given. Be aware, this is not friendly with Dispatchers.IO,
     * since [RemoteErrorEmitter] is intended to display messages to the user about error from the server/DB.
     * @param callFunction is the function that is returning the wanted object. Eg:
     * override suspend fun loginUser(body: LoginUserBody, emitter: RemoteErrorEmitter): LoginUserResponse?  = safeApiCall( { authApi.loginUser(body)} , emitter)
     * @param emitter is the interface that handles the error messages. The error messages must be displayed on the MainThread, or else they would throw an Exception.
     */
}
