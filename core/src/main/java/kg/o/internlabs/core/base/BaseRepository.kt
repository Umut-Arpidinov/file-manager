package kg.o.internlabs.core.base

import kg.o.internlabs.core.common.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class BaseRepository {

    fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Flow<ApiState<T>> = flow {
        emit(ApiState.Loading)

        val response = apiCall()
        if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                emit(ApiState.Success(data))
            }
        } else{
           /* emit(ApiState.(response.errorBody()!!))*/
            val raw = response.errorBody()?.string()
            val jObject = raw?.let { org.json.JSONObject(it) }
            val str: String = jObject?.get("message").toString()
            emit(ApiState.Failure(Throwable(str)))
        }
    }. catch {e->
        e.printStackTrace()
        emit(ApiState.Failure(Exception(e)))
    }.flowOn(Dispatchers.IO)
}

