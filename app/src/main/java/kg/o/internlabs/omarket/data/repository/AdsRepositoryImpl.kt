package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.omarket.data.mappers.MapperForModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.repository.AdsRepository
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AdsRepository, BaseRepository() {

    private val mapper = MapperForModels()

    override fun getCategories(token: String) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForCategories(
            apiService.getCategories(
                token
            )
        )
    }

    override fun getAds(page: Int, token: String) = safeApiCall {
        apiService.getAds(token, page)
    }
}