package kg.o.internlabs.core.remote

import kg.o.internlabs.core.model.Login
import kg.o.internlabs.core.model.Otp
import kg.o.internlabs.core.model.RefreshToken
import kg.o.internlabs.core.model.Register
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
            Call<Register>

    @POST("api/market-auth/check-otp/")
    fun checkOtp(
        @Header("API_KEY") apiKey: String,
        @Body reg: Otp?
    ):
            Call<Otp>

    @POST("api/market-auth/refresh-token/")
    fun refreshToken(
        @Header("API_KEY") apiKey: String,
        @Body reg: RefreshToken?
    ):
            Call<RefreshToken>

    @POST("api/market-auth/auth/msisdn-password/")
    fun loginUser(
        @Header("API_KEY") apiKey: String,
        @Body reg: Login?
    ):
            Call<Login>


}