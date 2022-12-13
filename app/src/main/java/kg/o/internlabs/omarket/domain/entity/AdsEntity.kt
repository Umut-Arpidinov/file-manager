package kg.o.internlabs.omarket.domain.entity

import com.google.gson.annotations.SerializedName
import kg.o.internlabs.omarket.data.remote.model.ads.MainResult

data class AdsEntity(
    val details: String?,
    val errorCode: Int?,
    val result: MainResult?,
    val resultCode: String?
)