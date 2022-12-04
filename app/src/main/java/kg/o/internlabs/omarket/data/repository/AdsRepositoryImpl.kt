package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.MapperForModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.CategoriesEntity
import kg.o.internlabs.omarket.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AdsRepository, BaseRepository() {

    private val mapper = MapperForModels()

    override fun getCategories(token: String): Flow<ApiState<CategoriesEntity>> = safeApiCall {
        mapper.mapRespDbModelToRespEntityForCategories(
            apiService.getCategories(
                token
            )
        )
    }
}