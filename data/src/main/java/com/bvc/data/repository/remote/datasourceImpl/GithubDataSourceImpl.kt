package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.remote.api.GithubApi
import com.bvc.data.remote.model.GithubResponse
import com.bvc.data.repository.remote.datasource.GithubDataSource
import com.bvc.data.utils.base.BaseRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class GithubDataSourceImpl
    @Inject
    constructor(
        private val githubApi: GithubApi,
    ) : BaseRepository(),
        GithubDataSource {
        override suspend fun getGithub(
            remoteErrorEmitter: RemoteErrorEmitter,
            owner: String,
        ): List<GithubResponse>? = safeApiCall(remoteErrorEmitter) { githubApi.getRepos(owner).body() }
    }
