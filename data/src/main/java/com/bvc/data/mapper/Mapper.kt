package com.bvc.data.mapper

import com.bvc.data.model.YoReponse
import com.bvc.domain.model.YoEntity

object Mapper {

    fun mapperToRes(yoResponse: YoReponse.ResData): YoEntity.Res {
        return YoEntity.Res(
            total_count = yoResponse.total_count,
            incomplete_results = yoResponse.incomplete_results,
            items = yoResponse.items,
        )
    }
}
