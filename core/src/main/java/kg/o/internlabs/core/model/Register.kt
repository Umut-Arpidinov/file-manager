package kg.o.internlabs.core.model

import com.google.gson.annotations.SerializedName

data class Register(
    val msisdn: String?,
    val password: String?,
    val password2: String?,
    val message: String? = null
)

data class Otp(
    val msisdn: String?,
    val otp: String? ,
    val message: String? = null
)

data class Token(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    val message: String? = null
)

data class RegResponse(
    val success: String?,
    val message: String?
)

data class RefreshToken(
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
)


