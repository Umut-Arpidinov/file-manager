package kg.o.internlabs.core.data.remote.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


//object RetrofitClient {
//
//    private const val API_KEY = "ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq"
//
//
//    private fun interceptor(): Interceptor {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        return interceptor
//    }
//
//    private fun authInterceptor(): Interceptor {
//        val authInterceptor = Interceptor { chain ->
//            val request = chain.request().newBuilder().apply {
//                addHeader("Content-type", "application/json").addHeader("API_KEY", API_KEY)
//            }.build()
//            chain.proceed(request)
//        }
//        return authInterceptor
//    }
//
//    val okHttpClient = OkHttpClient.Builder()
//        .writeTimeout(20, TimeUnit.SECONDS)
//        .readTimeout(20, TimeUnit.SECONDS)
//        .connectTimeout(20, TimeUnit.SECONDS)
//        .addInterceptor(interceptor())
//        .addInterceptor(authInterceptor())
//        .build()
//}