package kg.o.internlabs.core.data.remote.network

import kg.o.internlabs.core.data.remote.network.model.Register
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/market-auth/register/")
    fun registerUser(
        @Body reg: Register?
    ): Call<Register>

    @POST("api/market-auth/check-otp/")
    fun checkOtp(
        @Body otp: Register?
    ): Call<Register>

    @POST("api/market-auth/refresh-token/")
    fun refreshToken(
        @Body reg: Register?
    ): Call<Register>

    @POST("api/market-auth/auth/msisdn-password/")
    fun loginUser(
        @Body reg: Register?
    ): Call<Register>
}