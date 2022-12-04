package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.FAQEntity
import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getFaq(token: String): Flow<ApiState<FAQEntity>>

    fun getMyActiveAds(token: String, myAds: MyAdsEntity): Flow<ApiState<MyAdsEntity>>

    fun getMyNonActiveAds(token: String, myAds: MyAdsEntity): Flow<ApiState<MyAdsEntity>>

    fun getMyAds(token: String, myAds: MyAdsEntity): Flow<ApiState<MyAdsEntity>>

}