package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.ads.AdsDto
import kg.o.internlabs.omarket.domain.entity.CategoriesEntity
import kotlinx.coroutines.flow.Flow

interface AdsRepository {

    fun getCategories(token: String): Flow<ApiState<CategoriesEntity>>

    fun getAds(page: Int, token: String): Flow<ApiState<AdsDto?>>

}