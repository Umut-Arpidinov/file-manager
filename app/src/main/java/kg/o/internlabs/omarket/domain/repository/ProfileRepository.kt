package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.AvatarDelEntity
import kg.o.internlabs.omarket.domain.entity.AvatarEntity
import kg.o.internlabs.omarket.domain.entity.FAQEntity
import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {

    fun getFaq(token: String): Flow<ApiState<FAQEntity>>

    fun getMyActiveAds(token: String, myAds: MyAdsEntity, page: Int): Flow<ApiState<MyAdsEntity>>

    fun getMyNonActiveAds(token: String, myAds: MyAdsEntity, page: Int): Flow<ApiState<MyAdsEntity>>

    fun uploadAvatar(token: String, body: MultipartBody.Part): Flow<ApiState<AvatarEntity>>

    fun deleteAvatar(token: String): Flow<ApiState<AvatarDelEntity>>
}