package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun checkOtp(reg: RegisterEntity): Flow<ApiState<RegisterEntity>>
    fun loginUser(reg: RegisterEntity): Flow<ApiState<RegisterEntity>>
    fun refreshToken(reg:RegisterEntity): Flow<ApiState<RegisterEntity>>
    fun registerUser(reg: RegisterEntity): Flow<ApiState<RegisterEntity>>
}