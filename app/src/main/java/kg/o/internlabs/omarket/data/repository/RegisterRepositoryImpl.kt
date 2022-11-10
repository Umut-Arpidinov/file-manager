package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.repository.RegisterRepository
import kg.o.internlabs.omarket.toDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RegisterRepository, BaseRepository() {

    override fun checkOtp(reg: RegisterEntity): Flow<ApiState<RegisterDto>> = safeApiCall {
        apiService.checkOtp(reg.toDto())
    }

    override fun loginUser(reg: RegisterEntity): Flow<ApiState<RegisterDto>> = safeApiCall {
        apiService.loginUser(reg.toDto())
    }

    override fun refreshToken(reg: RegisterEntity): Flow<ApiState<RegisterDto>> = safeApiCall {
        apiService.refreshToken(reg.toDto())
    }

    override fun registerUser(reg: RegisterEntity): Flow<ApiState<RegisterDto>> = safeApiCall {
        apiService.registerUser(reg.toDto())
    }
}