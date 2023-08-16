package com.droi.di

import com.droi.domain.repository.YoRepository
import com.droi.domain.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetUserRepoUseCase(repository: YoRepository) = GetUserUseCase(repository)
}