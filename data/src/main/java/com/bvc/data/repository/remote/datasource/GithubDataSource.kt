package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.GithubResponse
import com.bvc.domain.utils.RemoteErrorEmitter

interface GithubDataSource {
    suspend fun getGithub(
        remoteErrorEmitter: RemoteErrorEmitter,
        owner: String,
    ): List<GithubResponse>?
}
