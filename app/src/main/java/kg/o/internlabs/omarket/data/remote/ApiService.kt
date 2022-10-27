package kg.o.internlabs.omarket.data.remote

import kg.o.internlabs.core.data.remote.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/market-auth/register/")
    fun registerUser(
        @Body reg: Register?
    ): Call<RegResponse>

    @POST("api/market-auth/check-otp/")
    fun checkOtp(
        @Body otp: Otp?
    ): Call<Token>

    @POST("api/market-auth/refresh-token/")
    fun refreshToken(
        @Body reg: RefreshToken?
    ): Call<Token>

    @POST("api/market-auth/auth/msisdn-password/")
    fun loginUser(
        @Body reg: Login?
    ): Call<Token>
}