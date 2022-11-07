package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.omarket.base.BaseRepository
import kg.o.internlabs.omarket.common.ApiState
import kg.o.internlabs.omarket.data.remote.ApiService
import kg.o.internlabs.omarket.data.remote.model.Register
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckOtpRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CheckOtpRepository, BaseRepository() {
    override fun checkOtp(reg: Register): Flow<ApiState<Register>> = safeApiCall {
        apiService.checkOtp(reg)
    }
}