package kg.o.internlabs.omarket.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.o.internlabs.omarket.data.mappers.MapperForFAQAndProfileModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kg.o.internlabs.omarket.domain.entity.MyAdsResultsEntity

class ProfilePagingSource (
    private val apiService: ApiService,
    private val myAds: MyAdsEntity,
    private val token: String
) :
    PagingSource<Int, MyAdsResultsEntity>() {

    private val map = MapperForFAQAndProfileModels()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyAdsResultsEntity> {
        println("=====================")
        return try {
            val position = params.key ?: 1
            val response = map.mapRespDbModelToRespEntityForMyAds(
                apiService.getMyAds(token, map.mapEntityToDbModel(myAds), position))
            println("===1=  == "+ response?.body().toString())

            if (response?.body() == null) throw Exception("null")
            println("==2=== "+response.body()!!.result.toString())

            if (response.body()!!.result == null) throw Exception("1"+response.body()!!.resultCode)
            println("===3=== "+response.body()!!.result!!.results.toString())

            if (response.body()!!.result!!.results == null) throw Exception("2"+response.body()!!.resultCode)
            val res = response.body()!!.result!!.results
            println("====4==="+ (res?.size))
            LoadResult.Page(
                data = res.orEmpty(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            println("+++++++++++"+e.message)
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MyAdsResultsEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
