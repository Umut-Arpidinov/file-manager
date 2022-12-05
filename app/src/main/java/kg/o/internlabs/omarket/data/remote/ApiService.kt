package kg.o.internlabs.omarket.data.remote


import kg.o.internlabs.omarket.data.remote.model.CategoriesDto
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/market-auth/register/")
    suspend fun registerUser(
        @Body reg: RegisterDto?
    ): Response<RegisterDto?>

    @POST("/api/market-auth/check-otp/")
    suspend fun checkOtp(
        @Body otp: RegisterDto?
    ): Response<RegisterDto?>

    @POST("api/market-auth/refresh-token/")
    suspend fun refreshToken(
        @Body reg: RegisterDto?
    ): Response<RegisterDto?>

    @POST("api/market-auth/auth/msisdn-password/")
    suspend fun loginUser(
        @Body reg: RegisterDto?
    ): Response<RegisterDto?>

    @GET("api/ads-board/v1/category/list")
    suspend fun getCategories(
        @Header("Authorization") token: String?
    ): Response<CategoriesDto?>
}