package com.bvc.data.mapper

import com.bvc.data.remote.model.GithubResponse
import com.bvc.domain.model.GithubEntity

object Mapper {
    fun mapperGithub(response: List<GithubResponse>?): List<GithubEntity>? =
        if (response != null) {
            response.toDomain()
        } else {
            null
        }

    fun List<GithubResponse>.toDomain(): List<GithubEntity> =
        this.map {
            GithubEntity(
                it.name,
                it.id,
                it.date,
                it.url,
            )
        }
}
