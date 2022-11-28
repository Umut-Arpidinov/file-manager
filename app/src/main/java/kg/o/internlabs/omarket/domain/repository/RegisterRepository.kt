package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun checkOtp(reg: RegisterEntity): Flow<ApiState<RegisterDto>>
    fun loginUser(reg: RegisterEntity): Flow<ApiState<RegisterDto>>
    fun refreshToken(reg:RegisterEntity): Flow<ApiState<RegisterDto>>
    fun registerUser(reg: RegisterEntity): Flow<ApiState<RegisterDto>>
}