package kg.o.internlabs.omarket.data.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterDto(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    val message: String? = null,
    val msisdn: String? = null,
    val otp: String? = null ,
    val success: String? = null,
    val password: String? = null,
    val password2: String? = null,
)


