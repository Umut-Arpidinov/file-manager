package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.omarket.data.mappers.MapperForCrudAds
import kg.o.internlabs.omarket.data.mappers.MapperForFAQAndProfileModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.repository.CRUDAdsRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class CrudAdsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CRUDAdsRepository, BaseRepository() {

    private val map = MapperForCrudAds()
    private val mapper = MapperForFAQAndProfileModels()

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

    override fun deleteImageFromAd(token: String, uuid: String) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForDelImg(
            apiService.deleteImageFromAd(token, uuid)
        )
    }
}