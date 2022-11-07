package kg.o.internlabs.omarket.domain.repository

import kg.o.internlabs.omarket.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.Register
import kotlinx.coroutines.flow.Flow

interface CheckOtpRepository {

    fun checkOtp(reg: Register): Flow<ApiState<Register>>

}