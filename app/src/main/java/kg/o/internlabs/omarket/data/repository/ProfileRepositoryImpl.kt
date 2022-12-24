package kg.o.internlabs.omarket.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.omarket.data.mappers.MapperForFAQAndProfileModels
import kg.o.internlabs.omarket.data.paging.FaqPagingSource
import kg.o.internlabs.omarket.data.paging.ProfilePagingSource
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kg.o.internlabs.omarket.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProfileRepository, BaseRepository() {

    private val mapper = MapperForFAQAndProfileModels()

    override fun getFaq(token: String) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 1, maxSize = 60,
            initialLoadSize = 20),
        pagingSourceFactory = {
            FaqPagingSource(
                apiService,
                token
            )
        }
    ).flow

    override fun getMyAds(token: String, myAds: MyAdsEntity) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 1, maxSize = 60,
            initialLoadSize = 20),
        pagingSourceFactory = {
            ProfilePagingSource(
                apiService,
                mapper.mapEntityToDbModel(myAds), token
            )
        }
    ).flow

    override fun getMyAllAds(token: String, myAds: MyAdsEntity) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForMyAds(
            apiService.getMyAds(
                token,
                mapper.mapEntityToDbModel(myAds),
                null
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