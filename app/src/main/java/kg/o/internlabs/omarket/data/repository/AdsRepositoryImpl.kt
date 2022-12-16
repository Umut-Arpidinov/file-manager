package kg.o.internlabs.omarket.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.omarket.data.mappers.MapperForAds
import kg.o.internlabs.omarket.data.mappers.MapperForModels
import kg.o.internlabs.omarket.data.paging.AdsPagingSource
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.ads.AdsByCategory
import kg.o.internlabs.omarket.domain.repository.AdsRepository
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AdsRepository, BaseRepository() {

    private val mapper = MapperForModels()
    private val map = MapperForAds()

    override fun getCategories(token: String) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForCategories(
            apiService.getCategories(
                token
            )
        )
    }

  /*  override fun getAdsByCategory(token: String, ads: AdsByCategory?) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = {
            AdsByCategoryPagingSource(
                apiService,
                token,
                map.toDbModel(ads)
            )
        }
    ).flow*/

    override fun getAds(token: String, ads: AdsByCategory?) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = {
            AdsPagingSource(
                apiService,
                token,
                map.toDbModel(ads)
            )
        }
    ).flow

    override fun getAds(page: Int, token: String) = safeApiCall {
        apiService.getAds(token, page)
    }
}