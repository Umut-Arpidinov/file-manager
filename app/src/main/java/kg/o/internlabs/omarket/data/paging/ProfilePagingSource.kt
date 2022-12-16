package kg.o.internlabs.omarket.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.o.internlabs.omarket.data.mappers.MapperForFAQAndProfileModels
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.data.remote.model.MyAdsDto
import kg.o.internlabs.omarket.domain.entity.MyAdsResultsEntity

class   ProfilePagingSource(
    private val apiService: ApiService,
    private val myAds: MyAdsDto,
    private val token: String
) :
    PagingSource<Int, MyAdsResultsEntity>() {

    private val map = MapperForFAQAndProfileModels()

    override suspend fun load(params: LoadParams<Int>) = try {
        val position = params.key ?: 1
        val response = map.mapRespDbModelToRespEntityForMyAds(
            apiService.getMyAds(token, myAds, position)
        )
        if (response?.body() == null) throw Exception("null")

        if (response.body()!!.result == null) {
            throw Exception("*1*" + response.body()!!.resultCode)
        }

        if (response.body()!!.result!!.results == null) {
            throw Exception("*2*" + response.body()!!.resultCode)
        }

        val res = response.body()!!.result!!.results
        LoadResult.Page(
            data = res.orEmpty(),
            prevKey = if (position == 1) null else position - 1,

            nextKey = if (response.body()?.result?.next != null) position + 1 else null
        )
    } catch (e: Exception) {
        println("+++++++++++" + e.message)
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, MyAdsResultsEntity>) =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}
