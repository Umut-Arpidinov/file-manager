package kg.o.internlabs.omarket.domain.repository

import androidx.paging.PagingData
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {

    fun getFaq(token: String): Flow<ApiState<FAQEntity>>

    fun getMyAds(token: String, myAds: MyAdsEntity):
            Flow<PagingData<MyAdsResultsEntity>>//Flow<ApiState<MyAdsEntity>>

    fun uploadAvatar(token: String, body: MultipartBody.Part): Flow<ApiState<AvatarEntity>>

    fun deleteAvatar(token: String): Flow<ApiState<AvatarDelEntity>>
}