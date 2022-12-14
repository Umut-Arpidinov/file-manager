package kg.o.internlabs.omarket.data.remote.model.ads

import com.google.gson.annotations.SerializedName
import kg.o.internlabs.omarket.domain.entity.ads.MainResult

data class AdsDto(
    @SerializedName("details")
    val details: String? = null,
    @SerializedName("errorCode")
    val errorCode: Int? = null,
    @SerializedName("result")
    val result: MainResult? = null,
    @SerializedName("resultCode")
    val resultCode: String? = null
)