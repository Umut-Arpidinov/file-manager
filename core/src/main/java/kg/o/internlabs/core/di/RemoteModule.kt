package kg.o.internlabs.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val API_KEY = "ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq"

    @Provides
    @Singleton
    fun interceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    @Singleton
    fun authInterceptor(): Interceptor {
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder().apply {
                addHeader("Content-type", "application/json").addHeader("API_KEY",
                    API_KEY
                )
            }.build()
            chain.proceed(request)
        }
        return authInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor())
            .addInterceptor(authInterceptor())
            .build()
    }

}