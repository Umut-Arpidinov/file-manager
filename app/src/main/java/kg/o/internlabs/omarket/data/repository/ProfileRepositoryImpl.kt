package kg.o.internlabs.omarket.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.omarket.data.mappers.MapperForFAQAndProfileModels
import kg.o.internlabs.omarket.data.paging.ProfilePagingSource
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kg.o.internlabs.omarket.domain.entity.MyAdsResultsEntity
import kg.o.internlabs.omarket.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

/*
class MoviePagingSource(private val apiService: RetrofitService) : PagingSource<Int, MyAdsResultsDto>() {

    private val myAdsDto = MyAdsDto(statuses = listOf("active","cancelled","moderate","disabled"))

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyAdsResultsDto> {
        println("=====================")
        return try {
            val position = params.key ?: 1
            val response =
                apiService.getMyAds(
                    "eyJ1c2VyX2lkIjogNjUsICJ1dWlkIjogImE4MzI3NzNlMTg4NjQ0NjY4MTc4YWU1ZjhjYzg4MTVlIiwgImV4X3RpbWUiOiAxNjczMDY0OTg4fQ==:a5k7o_x-BEcE6vz1et1RetJDKtk.a832773e188644668178ae5f8cc8815e",
                    myAdsDto, position)
            println("===1=  == "+response.body().toString())

            if (response.body() == null) throw Exception("null")
            println("==2=== "+response.body()!!.result.toString())

            if (response.body()!!.result == null) throw Exception("1"+response.body()!!.resultCode)
            println("===3=== "+response.body()!!.result!!.results.toString())

            if (response.body()!!.result!!.results == null) throw Exception("2"+response.body()!!.resultCode)
            val res: List<MyAdsResultsDto> = response.body()!!.result!!.results!!
            println("====4==="+res.size)
            LoadResult.Page(
                data = res,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            println("+++++++++++"+e.message)
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MyAdsResultsDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
*/




class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProfileRepository, BaseRepository() {

    private val mapper = MapperForFAQAndProfileModels()

    override fun getFaq(token: String) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForFaq(
            apiService.getFaq(
                token
            )
        )
    }

  /*  Flow<PagingData<Character>> = Pager(
    config = PagingConfig(pageSize = 20, prefetchDistance = 2),
    pagingSourceFactory = { CharactersPagingDataSource(service) }
    ).flow*/
    override fun getMyAds(token: String, myAds: MyAdsEntity):
            Flow<PagingData<MyAdsResultsEntity>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = { ProfilePagingSource(apiService, myAds, token)}
            ).flow
    /* override fun getMyActiveAds(token: String, myAds: MyAdsEntity, page: Int) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForMyAds(
            apiService.getMyAds(
                token,
                mapper.mapEntityToDbModel(
                    myAds
                ),
                page
            )
        )
    }*/

/*
override fun getMyNonActiveAds(token: String, myAds: MyAdsEntity, page: Int) = safeApiCall {
        mapper.mapRespDbModelToRespEntityForMyAds(
            apiService.getMyAds(
                token,
                mapper.mapEntityToDbModel(
                    myAds
                ),
                page
            )
        )
    }
*/

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