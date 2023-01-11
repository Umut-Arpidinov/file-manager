package kg.o.internlabs.omarket.domain.repository

import androidx.paging.PagingData
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {

    fun getFaq(token: String): Flow<PagingData<ResultsEntity>>

    fun getMyAds(token: String, myAds: MyAdsEntity): Flow<PagingData<MyAdsResultsEntity>>

    fun getMyAllAds(token: String, myAds: MyAdsEntity): Flow<ApiState<MyAdsEntity>>

    fun uploadAvatar(token: String, body: MultipartBody.Part): Flow<ApiState<UploadImageEntity>>

    fun deleteAvatar(token: String): Flow<ApiState<DeleteEntity>>
}