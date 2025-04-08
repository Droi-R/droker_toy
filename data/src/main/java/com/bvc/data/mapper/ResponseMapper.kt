package com.bvc.data.mapper

import com.bvc.data.remote.model.response.AffiliateResponse
import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.GithubResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.OptionResponse
import com.bvc.data.remote.model.response.OrderMemoResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.ProductOptionResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.ResMeta
import com.bvc.data.remote.model.response.ResPagination
import com.bvc.data.remote.model.response.StockResponse
import com.bvc.data.remote.model.response.SubCategoryResponse
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.Data
import com.bvc.domain.model.DataList
import com.bvc.domain.model.GithubEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.Meta
import com.bvc.domain.model.Options
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.OrderMemo
import com.bvc.domain.model.Pagination
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.ProductOptionEntity
import com.bvc.domain.model.Stock
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.type.OrderFrom

object ResponseMapper {
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

    fun mapProducts(response: ResDataList<ProductResponse>?): DataList<ProductEntity> =
        DataList(
            meta = mapMeta(response?.meta),
            pagination = mapPagination(response?.pagination),
            data = response?.data?.map { it.toEntity() },
        )

    fun mapOrder(response: ResData<OrderResponse>?): Data<OrderEntity> =
        Data(
            meta = mapMeta(response?.meta),
            data =
                OrderEntity(
                    oid = response?.data?.oid ?: "",
                    orderName = response?.data?.orderName ?: "",
                    orderItems =
                        response
                            ?.data
                            ?.orderItems
                            ?.map { it.toEntity() }
                            ?.let { ArrayList(it) } ?: arrayListOf(),
                    buyerName = response?.data?.buyerName ?: "",
                    orderFrom = response?.data?.orderFrom ?: OrderFrom.POS,
                    tableNumber = response?.data?.tableNumber ?: 0,
                    buyerPhoneNumber = response?.data?.buyerPhoneNumber ?: "",
                    additionalComment = response?.data?.additionalComment ?: "",
                    orderNumber = response?.data?.orderNumber ?: 0,
                    orderMemos =
                        response
                            ?.data
                            ?.orderMemos
                            ?.map { it.toEntity() }
                            ?.let { ArrayList(it) } ?: arrayListOf(),
                ),
        )

    // ----- Private extension functions -----

    private fun ProductResponse.toEntity(): ProductEntity =
        ProductEntity(
            externalKey = externalKey,
            name = name,
            categoryKey = categoryKey,
            categoryName = categoryName,
            descriptions = descriptions,
            isVat = isVat,
            selected = selected,
            stock = stock.toEntity(),
            color = color,
            image = image,
            price = price,
            productOption = productOption.map { it.toEntity() },
            position = position,
        )

    private fun StockResponse.toEntity(): Stock =
        Stock(
            externalKey = externalKey,
            useStock = useStock,
            count = count,
        )

    private fun ProductOptionResponse.toEntity(): ProductOptionEntity =
        ProductOptionEntity(
            id = id,
            name = name,
            required = required,
            minOptionCountLimit = minOptionCountLimit,
            maxOptionCountLimit = maxOptionCountLimit,
            position = position,
            options = options.toEntities(),
        )

    private fun List<OptionResponse>.toEntities(): ArrayList<Options> =
        ArrayList(
            map {
                Options(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    position = it.position,
                    useStock = it.useStock,
                    stockQuantity = it.stockQuantity,
                    isSoldOut = it.isSoldOut,
                    isSelected = it.isSelected,
                )
            },
        )

    fun OrderMemoResponse.toEntity(): OrderMemo =
        OrderMemo(
            externalKey = externalKey ?: "",
            oid = oid ?: "",
            content = content ?: "",
            createdAt = createdAt ?: "",
            updatedAt = updatedAt ?: "",
        )
}
