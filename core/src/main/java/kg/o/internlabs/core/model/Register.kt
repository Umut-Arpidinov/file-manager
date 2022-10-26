package kg.o.internlabs.core.model

import com.google.gson.annotations.SerializedName

data class Register(
    val msisdn: String?,
    val password: String?,
    val password2: String?
)

data class Otp(
    val msisdn: String? = null,
    val otp: String? = null
)

data class Token(
    @SerializedName("access_token")
    val accessToken: String? = null,
    @SerializedName("refresh_token")
    val refreshToken: String?
)


