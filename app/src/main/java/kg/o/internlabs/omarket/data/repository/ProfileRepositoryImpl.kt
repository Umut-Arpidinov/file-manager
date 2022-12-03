package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.MapperForModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.FAQEntity
import kg.o.internlabs.omarket.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProfileRepository, BaseRepository() {

    private val mapper = MapperForModels()

    override fun getFaq(token: String): Flow<ApiState<FAQEntity>> = safeApiCall {
        mapper.mapRespDbModelToRespEntityForFaq(
            apiService.getFaq(
                token
            )
        )
    }
}