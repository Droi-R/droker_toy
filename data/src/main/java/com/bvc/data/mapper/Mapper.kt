package com.bvc.data.mapper

import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.CategoryResponse
import com.bvc.data.remote.model.GithubResponse
import com.bvc.data.remote.model.LoginResponse
import com.bvc.data.remote.model.ProductResponse
import com.bvc.data.remote.model.ResData
import com.bvc.data.remote.model.ResDataList
import com.bvc.data.remote.model.ResMeta
import com.bvc.data.remote.model.ResPagination
import com.bvc.data.remote.model.SubCategoryResponse
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.Data
import com.bvc.domain.model.DataList
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.Meta
import com.bvc.domain.model.Options
import com.bvc.domain.model.Pagination
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.ProductOptionEntity
import com.bvc.domain.model.Stock
import com.bvc.domain.model.SubCategoryEntity

object Mapper {
    fun mapGithub(response: List<GithubResponse>?): List<GithubEntity> =
        response?.map {
            GithubEntity(
                id = it.id,
                name = it.name,
                date = it.date,
                url = it.url,
            )
        } ?: emptyList()

    private fun mapMeta(meta: ResMeta?): Meta =
        Meta(
            code = meta?.code ?: 0,
            message = meta?.message ?: "",
        )

    private fun mapPagination(pagination: ResPagination?): Pagination =
        Pagination(
            next = pagination?.next ?: "",
            previous = pagination?.previous ?: "",
            count = pagination?.count ?: 0,
            nextCursor = pagination?.nextCursor ?: "",
        )

    fun mapLogin(response: ResData<LoginResponse>?): Data<LoginEntity> =
        Data(
            meta = mapMeta(response?.meta),
            data =
                LoginEntity(
                    access = response?.data?.access ?: "",
                    refresh = response?.data?.refresh ?: "",
                    mobilePhoneNumber = response?.data?.mobilePhoneNumber ?: "",
                ),
        )

    fun mapAffiliate(response: ResDataList<AffiliateResponse>?): DataList<AffiliateEntity> =
        DataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data =
                response?.data?.map {
                    AffiliateEntity(
                        name = it.name,
                        type = it.type,
                    )
                },
        )

    fun mapCategory(response: ResDataList<CategoryResponse>?): DataList<CategoryEntity> =
        DataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data =
                response?.data?.map {
                    CategoryEntity(
                        id = it.id,
                        name = it.name,
                        selected = it.selected,
                    )
                },
        )

    fun mapSubCategory(response: ResDataList<SubCategoryResponse>?): DataList<SubCategoryEntity> =
        DataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data =
                response?.data?.map {
                    SubCategoryEntity(
                        id = it.id,
                        name = it.name,
                        selected = it.selected,
                    )
                },
        )

    fun mapProduct(response: ResDataList<ProductResponse>?): DataList<ProductEntity> =
        DataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data =
                response?.data?.map {
                    ProductEntity(
                        id = it.id,
                        name = it.name,
                        categoryKey = it.categoryKey,
                        categoryName = it.categoryName,
                        descriptions = it.descriptions,
                        isVat = it.isVat,
                        selected = it.selected,
                        stock =
                            Stock(
                                externalKey = it.stock.externalKey,
                                useStock = it.stock.useStock,
                                count = it.stock.count,
                            ),
                        color = it.color,
                        image = it.image,
                        price = it.price,
                        productOption =
                            it.productOption.map { option ->
                                ProductOptionEntity(
                                    id = option.id,
                                    name = option.name,
                                    selected = option.selected,
                                    price = option.price,
                                    required = option.required,
                                    minOptionCountLimit = option.minOptionCountLimit,
                                    maxOptionCountLimit = option.maxOptionCountLimit,
                                    position = option.position,
                                    options =
                                        option.options.map { opt ->
                                            Options(
                                                id = opt.id,
                                                name = opt.name,
                                                price = opt.price,
                                                position = opt.position,
                                                useStock = opt.useStock,
                                                stockQuantity = opt.stockQuantity,
                                                isSoldOut = opt.isSoldOut,
                                                isSelected = opt.isSelected,
                                            )
                                        } as ArrayList<Options>,
                                )
                            },
                        position = it.position,
                    )
                } ?: emptyList(),
        )
}
