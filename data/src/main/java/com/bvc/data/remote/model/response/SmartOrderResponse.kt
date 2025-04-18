package com.bvc.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SmartOrderResponse(
    @SerializedName("smart_order_id")
    val smartOrderId: String? = "",
    @SerializedName("material_name")
    val materialName: String? = "",
    @SerializedName("stock")
    val stock: Int? = 0,
    @SerializedName("safety_stock")
    val safetyStock: Int? = 0,
    @SerializedName("description")
    val description: String? = "", // 제품설명, 원산지, 키로등
    @SerializedName("delivery_cost")
    val deliveryCost: String? = "", // 배송비
    @SerializedName("logistics_company")
    val logisticsCompany: String? = "", // 물류업체
    @SerializedName("expected_consumption")
    val expectedConsumption: String? = "", // 3일간 예상 소모량
    @SerializedName("current_stock")
    val currentStock: String? = "", // 현재 재고
    @SerializedName("origin")
    val origin: OriginResponse? = OriginResponse(),
    @SerializedName("material")
    val material: MaterialsResponse? = MaterialsResponse(),
) : Parcelable
