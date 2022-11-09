package kg.o.internlabs.omarket.data.repository

import kg.o.internlabs.core.base.BaseRepository
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.data.remote.model.Register
import kg.o.internlabs.omarket.domain.repository.CheckOtpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckOtpRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CheckOtpRepository, BaseRepository() {
    override fun checkOtp(reg: Register): Flow<ApiState<Register>> = safeApiCall {
        apiService.checkOtp(reg)
    }
}