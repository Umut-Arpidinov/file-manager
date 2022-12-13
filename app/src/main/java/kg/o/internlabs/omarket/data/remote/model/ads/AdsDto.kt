package kg.o.internlabs.omarket.data.remote.model.ads


import com.google.gson.annotations.SerializedName
import kg.o.internlabs.omarket.domain.entity.AdsEntity

data class AdsDto(
    @SerializedName("details")
    val details: String?,
    @SerializedName("errorCode")
    val errorCode: Int?,
    @SerializedName("result")
    val result: MainResult?,
    @SerializedName("resultCode")
    val resultCode: String?
)
fun AdsDto.toDomain() = AdsEntity(details, )