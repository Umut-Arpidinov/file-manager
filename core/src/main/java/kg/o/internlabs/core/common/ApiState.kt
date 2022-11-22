package kg.o.internlabs.core.common

import okhttp3.ResponseBody

// Состояние нашего запроса
sealed class ApiState<out T> {

    data class Success<out R>(val data: R) : ApiState<R>(
    )
    data class Failure(val msg: Throwable) : ApiState<Nothing>()
    data class FailureError(val msg: ResponseBody) : ApiState<Nothing>()
    object Loading: ApiState<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success -> "Success $data"
            is Failure -> "Failure $msg"
            is FailureError -> "Error 400 $msg"
            Loading -> "Loading"
        }
    }


}

