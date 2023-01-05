package kg.o.internlabs.omarket.data.mappers

import kg.o.internlabs.omarket.data.remote.model.InitiateAdDto
import kg.o.internlabs.omarket.domain.entity.InitiateAdEntity
import retrofit2.Response

class MapperForCrudAds {

    fun toEntity(v: InitiateAdDto?) = InitiateAdEntity(
        result = v?.result,
        resultCode = v?.resultCode,
        details = v?.details,
        errorCode = v?.errorCode
    )

    fun toRespEntityForInitiateAd(resp: Response<InitiateAdDto?>) =
        if (resp.isSuccessful) {
            Response.success(toEntity(resp.body()))
        } else {
            resp.errorBody()?.let { Response.error(resp.code(), it) }
        }
}