package kg.o.internlabs.omarket.data.remote


import androidx.lifecycle.LiveData
import kg.o.internlabs.omarket.data.remote.model.Register
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/market-auth/register/")
    suspend fun registerUser(
        @Body reg: Register?
    ): Response<Register>

    @POST("api/market-auth/check-otp/")
    suspend fun checkOtp(
        @Body otp: Register?
    ): Response<Register>

    @POST("api/market-auth/check-otp/")
    suspend fun checkOtpLiveData(
        @Body otp: Register?
    ): LiveData<Register>


    @POST("api/market-auth/refresh-token/")
    suspend fun refreshToken(
        @Body reg: Register?
    ): Response<Register>

    @POST("api/market-auth/auth/msisdn-password/")
    suspend fun loginUser(
        @Body reg: Register?
    ): Response<Register>
}