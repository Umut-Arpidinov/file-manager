package kg.o.internlabs.omarket.data

import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import retrofit2.Response

class MapperForModels {

    fun mapEntityToDbModel(reg: RegisterEntity?) = RegisterDto(
        accessToken = reg?.accessToken,
        refreshToken = reg?.refreshToken,
        message = reg?.message,
        msisdn = reg?.msisdn,
        otp = reg?.otp,
        success = reg?.success,
        password = reg?.password,
        password2 = reg?.password2
    )

    private fun mapDbModelToEntity(regDto: RegisterDto?) = RegisterEntity(
        accessToken = regDto?.accessToken,
        refreshToken = regDto?.refreshToken,
        message = regDto?.message,
        msisdn = regDto?.msisdn,
        otp = regDto?.otp,
        success = regDto?.success,
        password = regDto?.password,
        password2 = regDto?.password2
    )

    fun mapRespDbModelToRespEntity(list: Response<RegisterDto?>) = if (list.isSuccessful) {
        Response.success(mapDbModelToEntity(list.body()))
    } else {
        list.errorBody()?.let { Response.error(list.code(), it) }
    }
}