package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.remote.api.SplashApi
import com.bvc.data.remote.model.request.SignUpRequest
import com.bvc.data.remote.model.request.VerifySmsRequest
import com.bvc.data.remote.model.response.EmptyResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.StoreResponse
import com.bvc.data.repository.remote.datasource.SplashDataSource
import com.bvc.data.utils.base.BaseRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class SplashDataSourceImpl
    @Inject
    constructor(
        private val splashApi: SplashApi,
    ) : BaseRepository(),
        SplashDataSource {
        override suspend fun sendSms(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
        ): ResData<EmptyResponse>? =
            safeApiCall(remoteErrorEmitter) {
                splashApi
                    .sendSms(
                        sendSms = mapOf("phone_number" to phoneNum),
                    ).body()
            }

        override suspend fun verifySms(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ): ResData<EmptyResponse>? =
            safeApiCall(remoteErrorEmitter) {
                splashApi
                    .verifySms(
                        verifySmsRequest =
                            VerifySmsRequest(
                                phoneNumber = phoneNum,
                                certificationNumber = verification,
                            ),
                    ).body()
            }

        override suspend fun signUp(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ): ResData<LoginResponse>? =
            safeApiCall(remoteErrorEmitter) {
                splashApi
                    .signUp(
                        signUpRequest =
                            SignUpRequest(
                                phoneNumber = phoneNum,
                                certificationNumber = verification,
                            ),
                    ).body()
            }

        override suspend fun getLogin(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ): ResData<LoginResponse>? =
            safeApiCall(remoteErrorEmitter) {
                splashApi
                    .login(
                        verifySmsRequest =
                            VerifySmsRequest(
                                phoneNumber = phoneNum,
                                certificationNumber = verification,
                            ),
                    ).body()
            }

        override suspend fun getStore(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ResDataList<StoreResponse>? = safeApiCall(remoteErrorEmitter) { splashApi.getStore(token, id).body() }
    }
