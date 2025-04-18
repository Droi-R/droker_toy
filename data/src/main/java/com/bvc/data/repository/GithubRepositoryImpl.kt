package com.bvc.data.repository

import com.bvc.data.mapper.ResponseMapper
import com.bvc.data.repository.remote.datasource.GithubDataSource
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.repository.GithubRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class GithubRepositoryImpl
    @Inject
    constructor(
        private val githubDataSource: GithubDataSource,
    ) : GithubRepository {
        override suspend fun getGithub(
            remoteErrorEmitter: RemoteErrorEmitter,
            owner: String,
        ): List<GithubEntity> = ResponseMapper.mapGithub(githubDataSource.getGithub(remoteErrorEmitter, owner))
    }
