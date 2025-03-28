package com.bvc.ordering.di

import android.content.Context
import com.bvc.data.repository.PreferencesRepositoryImpl
import com.bvc.domain.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {
    @Provides
    @Singleton
    fun providePreferencesHelper(
        @ApplicationContext context: Context,
    ): PreferenceRepository = PreferencesRepositoryImpl(context)
}
