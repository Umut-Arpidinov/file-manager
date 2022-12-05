package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.omarket.data.local.mappers.MapperForFAQAndProfileModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kg.o.internlabs.omarket.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProfileRepository, BaseRepository() {

    private val mapper = MapperForFAQAndProfileModels()

    override fun getFaq(token: String) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForFaq(
            apiService.getFaq(
                token
            )
        )
    }

    override fun getMyActiveAds(token: String, myAds: MyAdsEntity) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForMyAds(
            apiService.getMyAds(
                token,
                mapper.mapEntityToDbModel(
                    myAds
                )
            )
        )
    }

    override fun getMyNonActiveAds(token: String, myAds: MyAdsEntity) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForMyAds(
            apiService.getMyAds(
                token,
                mapper.mapEntityToDbModel(
                    myAds
                )
            )
        )
    }

    override fun uploadAvatar(token: String, body: MultipartBody.Part) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForAvatar(
            apiService.uploadAvatar(token, body)
        )
    }

    override fun deleteAvatar(token: String) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForAvatarDel(
            apiService.deleteAvatar(token)
        )
    }

}