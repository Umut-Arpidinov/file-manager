package kg.o.internlabs.omarket.domain.entity

data class RegisterEntity(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val message: String? = null,
    val msisdn: String? = null,
    val otp: String? = null ,
    val success: String? = null,
    val password: String? = null,
    val password2: String? = null,
)
