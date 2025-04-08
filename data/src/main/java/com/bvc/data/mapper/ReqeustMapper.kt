package com.bvc.data.mapper

import com.bvc.data.remote.model.request.OptionsRequest
import com.bvc.data.remote.model.request.ProductItemsRequest
import com.bvc.data.remote.model.request.ProductOptionRequest
import com.bvc.data.remote.model.request.StockRequest
import com.bvc.domain.model.CartEntity
import com.bvc.domain.model.Options
import com.bvc.domain.model.ProductOptionEntity

fun CartEntity.toRequest(): ProductItemsRequest =
    ProductItemsRequest(
        externalKey = product.externalKey,
        name = product.name,
        categoryKey = product.categoryKey,
        categoryName = product.categoryName,
        descriptions = product.descriptions,
        isVat = product.isVat,
        selected = product.selected,
        stock =
            StockRequest(
                externalKey = product.stock.externalKey,
                useStock = product.stock.useStock,
                count = product.stock.count,
            ),
        color = product.color,
        image = product.image,
        price = product.price,
        productOption = product.productOption.map { it.toRequest() },
        position = product.position,
    )

fun ProductOptionEntity.toRequest(): ProductOptionRequest =
    ProductOptionRequest(
        id = id,
        name = name,
        required = required,
        minOptionCountLimit = minOptionCountLimit,
        maxOptionCountLimit = maxOptionCountLimit,
        position = position,
        options = ArrayList(options.map { it.toRequest() }),
    )

fun Options.toRequest(): OptionsRequest =
    OptionsRequest(
        id = id,
        name = name,
        price = price,
        position = position,
        useStock = useStock,
        stockQuantity = stockQuantity,
        isSoldOut = isSoldOut,
        isSelected = isSelected,
    )
