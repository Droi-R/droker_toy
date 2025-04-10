package com.bvc.ordering.di

import com.bvc.data.repository.OrderStoreRepositoryImpl
import com.bvc.data.repository.ProductStoreRepositoryImpl
import com.bvc.domain.repository.OrderStoreRepository
import com.bvc.domain.repository.ProductStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {
    @Binds
    @Singleton
    abstract fun bindCartStoreRepository(impl: ProductStoreRepositoryImpl): ProductStoreRepository

    @Binds
    @Singleton
    abstract fun bindOrderStoreRepository(impl: OrderStoreRepositoryImpl): OrderStoreRepository
}
