package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    fun checkOtp(reg: RegisterDto): Flow<ApiState<RegisterDto>>
    fun loginUser(reg: RegisterDto): Flow<ApiState<RegisterDto>>
    fun refreshToken(reg:RegisterDto): Flow<ApiState<RegisterDto>>
    fun registerUser(reg: RegisterDto): Flow<ApiState<RegisterDto>>

}