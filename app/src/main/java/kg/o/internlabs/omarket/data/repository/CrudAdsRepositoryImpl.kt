package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.omarket.data.mappers.MapperForAds
import kg.o.internlabs.omarket.data.mappers.MapperForCrudAds
import kg.o.internlabs.omarket.data.mappers.MapperForFAQAndProfileModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.DeletedImageUrlEntity
import kg.o.internlabs.omarket.domain.entity.EditAds
import kg.o.internlabs.omarket.domain.repository.CRUDAdsRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class CrudAdsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CRUDAdsRepository, BaseRepository() {

    private val map = MapperForCrudAds()
    private val mapper = MapperForFAQAndProfileModels()
    private val mapperForAds = MapperForAds()

    override fun getInitiatedAnAD(token: String) = safeApiCall {
        map.toRespEntityForInitiateAd(apiService.initiateAd(token))
    }

    override fun uploadImageToAd(
        token: String,
        body: MultipartBody.Part,
        uuid: String
    ) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForUploadImg(
            apiService.uploadImageToAd(token, body, uuid)
        )
    }

    override fun deleteImageFromAd(token: String, body: DeletedImageUrlEntity, uuid: String) = safeApiCall {
        map.toRespEntityForDelImg(
            apiService.deleteImageFromAd(
                token,
                map.toDbModel(body),
                uuid
            )
        )
    }

    override fun editAnAd(token: String, editAds: EditAds, uuid: String) = safeApiCall {
        mapperForAds.toRespEntityForEditAd(
            apiService.editAd(
                token,
                mapperForAds.toDbModel(editAds), uuid)
        )
    }
}