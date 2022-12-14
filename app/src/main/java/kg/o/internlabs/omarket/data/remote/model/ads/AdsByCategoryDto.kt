package kg.o.internlabs.omarket.data.remote.model.ads

import com.google.gson.annotations.SerializedName

data class AdsByCategoryDto(
    @SerializedName("details")
    val details: String? = null,
    @SerializedName("errorCode")
    val errorCode: Int? = null,
    @SerializedName("result")
    val resultL: List<MainResultDto>? = null,
    @SerializedName("resultCode")
    val resultCode: String? = null,
    val q: String? = null
)