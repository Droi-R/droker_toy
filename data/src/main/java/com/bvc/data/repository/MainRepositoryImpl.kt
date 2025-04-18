package com.bvc.data.repository

import com.bvc.data.mapper.ResponseMapper
import com.bvc.data.mapper.toRequest
import com.bvc.data.remote.model.request.ApproveRequest
import com.bvc.data.remote.model.request.OrderInfoRequest
import com.bvc.data.remote.model.request.OrderRequest
import com.bvc.data.remote.model.request.PaymentRequest
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.EmptyEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.PaymentEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SmartOrderEntity
import com.bvc.domain.model.Store
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.repository.MainRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentChannel
import com.bvc.domain.type.PaymentMethod
import com.bvc.domain.type.PaymentStatus
import com.bvc.domain.type.PaymentType
import javax.inject.Inject

class MainRepositoryImpl
    @Inject
    constructor(
        private val mainDataSource: MainDataSource,
    ) : MainRepository {
        override suspend fun refreshToken(token: String): ApiData<LoginEntity> =
            ResponseMapper.mapRefreshToken(mainDataSource.refreshToken(token))

        override suspend fun getStore(
            token: String,
            storeId: String,
        ): ApiData<Store> = ResponseMapper.mapStore(mainDataSource.getStore(token, storeId))

        override suspend fun getMenuCategory(
            token: String,
            storeId: String,
        ): ApiDataList<CategoryEntity> =
            ResponseMapper.mapCategory(
                mainDataSource.getMenuCategory(
                    token,
                    storeId,
                ),
            )

        override suspend fun getTableArea(
            token: String,
            storeId: String,
        ): ApiDataList<CategoryEntity> =
            ResponseMapper.mapTableArea(
                mainDataSource.getTableArea(
                    token,
                    storeId,
                ),
            )

        override suspend fun getSubCategory(
            token: String,
            storeId: String,
            mainCategoryId: String,
        ): ApiDataList<SubCategoryEntity> =
            ResponseMapper.mapSubCategory(
                mainDataSource.getSubCategory(
                    token,
                    storeId,
                    mainCategoryId,
                ),
            )

        override suspend fun getProducts(
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ): ApiDataList<ProductEntity> =
            ResponseMapper.mapProducts(
                mainDataSource.getProducts(
                    token,
                    storeId,
                    mainCategoryId,
                    subCategoryId,
                ),
            )

        override suspend fun getMaterials(
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ): ApiDataList<MaterialsEntity> =
            ResponseMapper.mapMaterials(
                mainDataSource.getMaterials(
                    token,
                    storeId,
                    mainCategoryId,
                    subCategoryId,
                ),
            )

        override suspend fun getSmartOrder(
            token: String,
            storeId: String,
        ): ApiDataList<SmartOrderEntity> =
            ResponseMapper.mapSmartOrder(
                mainDataSource.getSmartOrder(
                    token,
                    storeId,
                ),
            )

        override suspend fun postOrder(
            token: String,
            userId: String,
            storeId: String,
            productItems: List<ProductEntity>,
            orderFrom: OrderFrom,
            orderStatus: OrderStatus,
            tablesId: Int,
            itemMemo: String,
            totalPrice: Int,
            supplyPrice: Int,
            vatPrice: Int,
            discountPrice: Int,
        ): ApiData<OrderEntity> =
            ResponseMapper.mapOrder(
                mainDataSource.postOrder(
                    token = token,
                    orderRequest =
                        OrderRequest(
                            order =
                                OrderInfoRequest(
                                    storeId = storeId.toInt(),
                                    userId = userId.toInt(),
                                    tablesId = tablesId,
                                    orderFrom = orderFrom.name,
                                    orderStatus = orderStatus,
                                    itemMemo = itemMemo,
                                    totalPrice = totalPrice,
                                    supplyPrice = supplyPrice,
                                    vatPrice = vatPrice,
                                    discountPrice = discountPrice,
                                    finalPrice = totalPrice - discountPrice,
                                ),
                            productItems = productItems.map { it.toRequest() },
                        ),
                ),
            )

        override suspend fun postPayment(
            token: String,
            userId: String,
            storeId: String,
            orderProductIds: List<String>,
            totalPrice: String,
            paymentMethod: PaymentMethod,
            paymentChannel: PaymentChannel,
            paymentStatus: PaymentStatus,
            paymentType: PaymentType,
        ): ApiData<PaymentEntity> =
            ResponseMapper.mapPayment(
                mainDataSource.postPayment(
                    token,
                    PaymentRequest(
                        userId = userId,
                        storeId = storeId,
                        orderProductIds = orderProductIds,
                        totalPrice = totalPrice,
                        paymentMethod = paymentMethod,
                        paymentChannel = paymentChannel,
                        paymentStatus = paymentStatus,
                        paymentType = paymentType,
                    ),
                ),
            )

        override suspend fun requestCapture(
            token: String,
            paymentId: String,
            amount: String,
            deviceId: String,
            approvedId: String,
            approvedDate: String,
        ): ApiData<EmptyEntity> =
            ResponseMapper.mapEmpty(
                mainDataSource.requestCapture(
                    token = token,
                    captureRequest =
                        ApproveRequest(
                            paymentId = paymentId,
                            amount = amount,
                            deviceId = deviceId,
                            approvedId = approvedId,
                            approvedDate = approvedDate,
                        ),
                ),
            )

        override suspend fun requestRefund(
            token: String,
            paymentId: String,
            amount: String,
            deviceId: String,
            approvedId: String,
            approvedDate: String,
            refundApprovedId: String,
            refundApprovedDate: String,
        ): ApiData<EmptyEntity> =
            ResponseMapper.mapEmpty(
                mainDataSource.requestRefund(
                    token = token,
                    refundRequest =
                        ApproveRequest(
                            paymentId = paymentId,
                            amount = amount,
                            deviceId = deviceId,
                            approvedId = approvedId,
                            approvedDate = approvedDate,
                            refundApprovedId = refundApprovedId,
                            refundApprovedDate = refundApprovedDate,
                        ),
                ),
            )

        override suspend fun getTables(
            token: String,
            id: String,
        ): ApiDataList<TableEntity> = ResponseMapper.mapTables(mainDataSource.getTables(token, id))
    }
