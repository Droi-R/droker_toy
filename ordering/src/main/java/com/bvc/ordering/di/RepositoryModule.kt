package com.bvc.ordering.di

import android.content.Context
import com.bvc.data.repository.MainRepositoryImpl
import com.bvc.data.repository.PreferencesRepositoryImpl
import com.bvc.data.repository.SplashRepositoryImpl
import com.bvc.data.repository.remote.datasourceImpl.MainDataSourceImpl
import com.bvc.data.repository.remote.datasourceImpl.SplashDataSourceImpl
import com.bvc.domain.repository.MainRepository
import com.bvc.domain.repository.PreferenceRepository
import com.bvc.domain.repository.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providePreferencesRepository(
        @ApplicationContext context: Context,
    ): PreferenceRepository = PreferencesRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideSplashRepository(splashDataSourceImpl: SplashDataSourceImpl): SplashRepository =
        SplashRepositoryImpl(
            splashDataSourceImpl,
        )

    @Provides
    @Singleton
    fun provideMainRepository(mainRepositoryImpl: MainDataSourceImpl): MainRepository =
        MainRepositoryImpl(
            mainRepositoryImpl,
        )
}
