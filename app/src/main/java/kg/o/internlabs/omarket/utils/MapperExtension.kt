package kg.o.internlabs.omarket

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity

fun RegisterDto.toDomain() = RegisterEntity(
    accessToken = this.accessToken.orEmpty(),
    refreshToken = this.refreshToken.orEmpty(),
    message = this.message.orEmpty(),
    msisdn = this.msisdn.orEmpty(),
    otp = this.otp.orEmpty(),
    success = this.success.orEmpty(),
    password = this.password.orEmpty(),
    password2 = this.password2.orEmpty()
)

fun RegisterEntity.toDto() = RegisterDto(
    accessToken = this.accessToken.orEmpty(),
    refreshToken = this.refreshToken.orEmpty(),
    message = this.message.orEmpty(),
    msisdn = this.msisdn.orEmpty(),
    otp = this.otp.orEmpty(),
    success = this.success.orEmpty(),
    password = this.password.orEmpty(),
    password2 = this.password2.orEmpty()
)

/*
fun mapListDbModelToListEntity(list: List<ShopItemDto>) = list.map {
    mapDbModelToEntity(it)
}

fun ApiState<RegisterDto>.toEntityApiState(): ApiState<RegisterEntity> {
    val apiState: ApiState<RegisterEntity> = ApiState<RegisterEntity>()
    return apiState
}*/
