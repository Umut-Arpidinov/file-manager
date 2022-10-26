package kg.o.internlabs.core.remote

import kg.o.internlabs.core.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {


    @POST("api/market-auth/register/")
    fun registerUser(
        @Header("API_KEY") apiKey: String,
        @Body reg: Register?
    ):
            Call<RegResponse>

    @POST("api/market-auth/check-otp/")
    fun checkOtp(
        @Header("API_KEY") apiKey: String,
        @Body otp: Otp?
    ):
            Call<Token>

    @POST("api/market-auth/refresh-token/")
    fun refreshToken(
        @Header("API_KEY") apiKey: String,
        @Body reg: RefreshToken?
    ):
            Call<Token>

    @POST("api/market-auth/auth/msisdn-password/")
    fun loginUser(
        @Header("API_KEY") apiKey: String,
        @Body reg: Login?
    ):
            Call<Login>


}