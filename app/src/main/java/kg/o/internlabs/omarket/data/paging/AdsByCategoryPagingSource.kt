package kg.o.internlabs.omarket.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.o.internlabs.omarket.data.mappers.MapperForAds
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.data.remote.model.ads.AdsByCategoryDto
import kg.o.internlabs.omarket.domain.entity.ads.MainResult

class AdsByCategoryPagingSource(
    private val apiService: ApiService,
    private val token: String,
    private val ads: AdsByCategoryDto,
    ) :
    PagingSource<Int, MainResult>() {

    private val map = MapperForAds()

    override suspend fun load(params: LoadParams<Int>) = try {
        val position = params.key ?: 1
        val response = map.toRespEntityForAdsByCategory(
                apiService.getAdsByCategory(token, ads, position)
            )
        if (response?.body() == null) throw Exception("null")

        if (response.body()?.resultL == null) throw Exception("*1*  ${response.errorBody()}")

        val res = response.body()?.resultL
        LoadResult.Page(
            data = res.orEmpty(),
            prevKey = if (position == 1) null else position - 1,

            nextKey = if (res?.isEmpty() == true) position + 1 else null
        )
    } catch (e: Exception) {
        println("+++++++++++" + e.message)
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, MainResult>) =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}
