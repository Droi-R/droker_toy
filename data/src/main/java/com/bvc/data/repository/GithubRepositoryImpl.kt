package com.bvc.data.repository

import com.bvc.data.mapper.Mapper
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
        ): List<GithubEntity> = Mapper.mapGithub(githubDataSource.getGithub(remoteErrorEmitter, owner))
    }
