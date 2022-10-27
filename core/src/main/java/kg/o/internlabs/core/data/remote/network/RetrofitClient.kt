package kg.o.internlabs.core.data.remote.network

import androidx.viewbinding.BuildConfig
import kg.o.internlabs.core.BuildConfig.API_URL
import kg.o.internlabs.core.data.remote.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private fun interceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    private fun authInterceptor(): Interceptor {
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder().apply {
                addHeader("Content-type", "application/json")
            }.build()
            chain.proceed(request)
        }
        return authInterceptor
    }

    private val okHttpClient = OkHttpClient.Builder()
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(interceptor()).addInterceptor(authInterceptor())
        .build()


    val instance: ApiService by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiService::class.java)
    }

}