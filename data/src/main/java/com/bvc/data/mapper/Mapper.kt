package com.bvc.data.mapper

import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.GithubResponse
import com.bvc.data.remote.model.LoginResponse
import com.bvc.data.remote.model.ResData
import com.bvc.data.remote.model.ResDataList
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.Data
import com.bvc.domain.model.DataList
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.Meta
import com.bvc.domain.model.Pagination

object Mapper {
    fun mapperGithub(response: List<GithubResponse>?): List<GithubEntity> =
        response?.map {
            GithubEntity(
                id = it.id,
                name = it.name,
                date = it.date,
                url = it.url,
            )
        } ?: emptyList()

    fun mapperLogin(response: ResData<LoginResponse>?): Data<LoginEntity> =
        Data(
            meta =
                Meta(
                    code = response?.meta?.code ?: 0,
                    message = response?.meta?.message ?: "",
                ),
            data =
                LoginEntity(
                    access = response?.data?.access ?: "",
                    refresh = response?.data?.refresh ?: "",
                    mobilePhoneNumber = response?.data?.mobilePhoneNumber ?: "",
                ),
        )

    fun mapperAffiliate(response: ResDataList<AffiliateResponse>?): DataList<AffiliateEntity> =
        DataList(
            meta =
                Meta(
                    code = response?.meta?.code ?: 0,
                    message = response?.meta?.message ?: "",
                ),
            pagination =
                Pagination(
                    next = response?.pagination?.next ?: "",
                    previous = response?.pagination?.previous ?: "",
                    count = response?.pagination?.count ?: 0,
                    nextCursor = response?.pagination?.nextCursor ?: "",
                ),
            data =
                response?.data?.map {
                    AffiliateEntity(
                        name = it.name,
                        type = it.type,
                    )
                },
        )
}
