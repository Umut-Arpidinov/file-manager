package kg.o.internlabs.omarket.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.o.internlabs.omarket.data.mappers.MapperForAds
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.domain.entity.ads.ResultX

class AdsPagingSource(
    private val apiService: ApiService,
    private val token: String
) :
    PagingSource<Int, ResultX>() {

    private val map = MapperForAds()

    override suspend fun load(params: LoadParams<Int>) = try {
        val position = params.key ?: 1
        val response = map.toRespEntityForAds(
            apiService.getAds(token, position)
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

    override fun getRefreshKey(state: PagingState<Int, ResultX>) =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}
