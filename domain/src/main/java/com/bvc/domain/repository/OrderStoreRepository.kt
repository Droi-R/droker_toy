package com.bvc.domain.repository

import com.bvc.domain.model.OrderEntity

interface OrderStoreRepository {
    fun setOrder(item: OrderEntity)

    fun getOrder(): OrderEntity?

    fun clearOrder()
}
