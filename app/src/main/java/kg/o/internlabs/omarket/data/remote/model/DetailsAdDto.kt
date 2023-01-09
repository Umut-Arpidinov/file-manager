package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName
import kg.o.internlabs.omarket.data.remote.model.ads.ResultXDto

data class DetailsAdDto(
    val details: String? = null,
    val errorCode: Int? = null,
    @SerializedName("result")
    val resultX: ResultXDto? = null,
    val resultCode: String? = null
)