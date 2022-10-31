package kg.o.internlabs.omarket.data.remote

import kg.o.internlabs.core.BuildConfig
import kg.o.internlabs.core.data.remote.network.RetrofitClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val instance: ApiService by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitClient.okHttpClient)
            .build()

        retrofit.create(ApiService::class.java)
    }
}