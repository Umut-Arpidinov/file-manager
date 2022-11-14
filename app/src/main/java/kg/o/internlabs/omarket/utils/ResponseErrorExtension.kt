package kg.o.internlabs.omarket.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import okhttp3.ResponseBody

fun ResponseBody.getErrorMessage(): String{
    val typeToken = object: TypeToken<RegisterEntity>(){}
    val error = Gson().fromJson<RegisterEntity>(this.charStream(), typeToken.type)
    return error.message ?: ""
}