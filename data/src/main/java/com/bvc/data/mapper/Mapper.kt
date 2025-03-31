package com.bvc.data.mapper

import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.GithubResponse
import com.bvc.data.remote.model.LoginResponse
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.model.LoginEntity

object Mapper {
    fun mapperGithub(response: List<GithubResponse>?): List<GithubEntity>? = response?.toDomain()

    private fun List<GithubResponse>.toDomain(): List<GithubEntity> =
        this.map {
            GithubEntity(
                it.name,
                it.id,
                it.date,
                it.url,
            )
        }

    fun mapperLogin(response: List<LoginResponse>?): List<LoginEntity>? =
        response?.map {
            LoginEntity(
                isSuccess = it.isSuccess,
                apiMessage = it.apiMessage,
                access = it.access,
                refresh = it.refresh,
                mobilePhoneNumber = it.mobilePhoneNumber,
            )
        }

    fun mapperAffiliate(response: List<AffiliateResponse>?): List<AffiliateEntity>? =
        response?.map {
            AffiliateEntity(
                isSuccess = it.isSuccess,
                apiMessage = it.apiMessage,
                name = it.name,
            )
        }
}
