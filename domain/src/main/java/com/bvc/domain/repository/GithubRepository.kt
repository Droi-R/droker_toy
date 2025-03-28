package com.bvc.domain.repository

import com.bvc.domain.model.GithubEntity
import com.bvc.domain.utils.RemoteErrorEmitter

interface GithubRepository {
    suspend fun getGithub(
        remoteErrorEmitter: RemoteErrorEmitter,
        owner: String,
    ): List<GithubEntity>?
}
