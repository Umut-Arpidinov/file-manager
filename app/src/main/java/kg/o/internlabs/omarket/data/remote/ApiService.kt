package kg.o.internlabs.omarket.data.remote


import kg.o.internlabs.omarket.data.remote.model.*
import kg.o.internlabs.omarket.data.remote.model.ads.AdsByCategoryDto
import kg.o.internlabs.omarket.data.remote.model.ads.AdsDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

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

    @GET("api/ads-board/faq/")
    suspend fun getFaq(
        @Header("Authorization") token: String?,
        @Query("page") page: Int?
    ): Response<FAQDto?>

    @POST("api/ads-board/v1/user/my-ads/")
    suspend fun getMyAds(
        @Header("Authorization") token: String?,
        @Body myAdsDto: MyAdsDto?,
        @Query("page") page: Int?
    ): Response<MyAdsDto?>

    @POST("api/ads-board/trusted/ads-filter-old/")
    suspend fun getAdsByCategory(
        @Header("Authorization") token: String?,
        @Body adsDto: AdsByCategoryDto?,
        @Query("page") page: Int?
    ): Response<AdsDto?>

    @Multipart
    @POST("api/ads-board/v1/user/upload-avatar/")
    suspend fun uploadAvatar(
        @Header("Authorization") token: String?,
        @Part image: MultipartBody.Part
    ): Response<AvatarDto?>

    @DELETE("api/ads-board/v1/user/remove-avatar/")
    suspend fun deleteAvatar(
        @Header("Authorization") token: String?
    ): Response<AvatarDelDto?>

    @GET("/api/ads-board/v1/ads/list/")
    suspend fun getAds(
        @Header("Authorization") token: String?,
        @Query("page") page: Int
    ): Response<AdsDto?>
}