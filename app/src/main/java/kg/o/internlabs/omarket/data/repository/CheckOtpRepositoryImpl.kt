package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckOtpRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RegisterRepository, BaseRepository() {
    override fun checkOtp(reg: RegisterDto): Flow<ApiState<RegisterDto>> = safeApiCall {
        apiService.checkOtp(reg)
    }

    override fun loginUser(reg: RegisterDto): Flow<ApiState<RegisterDto>> = safeApiCall {
        apiService.loginUser(reg)
    }

    override fun refreshToken(reg: RegisterDto): Flow<ApiState<RegisterDto>> = safeApiCall {
        apiService.refreshToken(reg)
    }

    override fun registerUser(reg: RegisterDto): Flow<ApiState<RegisterDto>> = safeApiCall {
        apiService.registerUser(reg)
    }
}