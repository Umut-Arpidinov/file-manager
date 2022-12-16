package kg.o.internlabs.omarket.data.remote.model.ads

import com.google.gson.annotations.SerializedName

data class AdsDto(
    @SerializedName("details")
    val details: String? = null,
    @SerializedName("errorCode")
    val errorCode: Int? = null,
    @SerializedName("result")
    val result: MainResultDto? = null,
    @SerializedName("resultCode")
    val resultCode: String? = null
)