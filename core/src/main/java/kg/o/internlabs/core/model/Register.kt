package kg.o.internlabs.core.model

data class Register(
    val msisdn: String,
    val password: String,
    val password2: String
)

data class Otp(
    val msisdn: String,
    val otp: String
)

data class RefreshToken(
    val refreshToken: String
)
