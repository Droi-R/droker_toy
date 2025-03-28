package com.bvc.domain.usecase

import com.bvc.domain.repository.GithubRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class GetUserRepoUseCase
    @Inject
    constructor(
        private val githubRepository: GithubRepository,
    ) {
        suspend fun execute(
            remoteErrorEmitter: RemoteErrorEmitter,
            owner: String,
        ) = githubRepository.getGithub(remoteErrorEmitter, owner)
    }
