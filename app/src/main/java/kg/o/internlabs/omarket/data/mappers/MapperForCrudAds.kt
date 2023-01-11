package kg.o.internlabs.omarket.data.mappers

import kg.o.internlabs.omarket.data.remote.model.DeleteDto
import kg.o.internlabs.omarket.data.remote.model.DeletedImageUrlDto
import kg.o.internlabs.omarket.data.remote.model.InitiateAdDto
import kg.o.internlabs.omarket.domain.entity.DeleteEntity
import kg.o.internlabs.omarket.domain.entity.DeletedImageUrlEntity
import kg.o.internlabs.omarket.domain.entity.InitiateAdEntity
import retrofit2.Response

class MapperForCrudAds {

    private fun toEntity(v: InitiateAdDto?) = InitiateAdEntity(
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

    fun toDbModel(img: DeletedImageUrlEntity?) = DeletedImageUrlDto(
        url = img?.url
    )

    private fun toEntity(img: DeleteDto?) = DeleteEntity(
        result = img?.result,
        resultCode = img?.resultCode,
        details = img?.details,
        errorCode = img?.errorCode
    )

    fun toRespEntityForDelete(resp: Response<DeleteDto?>) =
        if (resp.isSuccessful) {
            Response.success(toEntity(resp.body()))
        } else {
            resp.errorBody()?.let { Response.error(resp.code(), it) }
        }
}