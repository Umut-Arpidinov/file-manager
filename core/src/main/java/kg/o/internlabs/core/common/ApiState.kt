package kg.o.internlabs.core.common

import okhttp3.Response


sealed class ApiState<out T> {

    data class Success<out R>(val data: R) : ApiState<R>(
    )

    data class Failure(val msg: Throwable) : ApiState<Nothing>(
    )
    object Loading: ApiState<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success -> "Success $data"
            is Failure -> "Failure ${msg}"
            Loading -> "Loading"
        }
    }


}
