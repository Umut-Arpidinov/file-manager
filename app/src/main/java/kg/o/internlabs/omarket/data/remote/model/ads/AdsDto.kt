package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName

data class AdsDto(
    @SerializedName("details")
    val details: String?,
    @SerializedName("errorCode")
    val errorCode: Int?,
    @SerializedName("result")
    val result: MainResultDto?,
    @SerializedName("resultCode")
    val resultCode: String?
)
//fun AdsDto.toDomain() = AdsEntity(details, )