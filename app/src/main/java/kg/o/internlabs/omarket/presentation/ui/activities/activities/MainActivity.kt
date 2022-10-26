package kg.o.internlabs.omarket.presentation.ui.activities.activities

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.core.model.*
import kg.o.internlabs.core.network.RetrofitClient
import kg.o.internlabs.omarket.databinding.ActivityMainBinding
import kg.o.internlabs.omarket.presentation.viewmodels.activitiesviewmodels.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Ref


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val register = Register("996500997007", "1234", "1234")
    private val otp = Otp("996500997007", "7197")
    var token: String = ""
    var refreshToken = RefreshToken("")



    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        //binding.navHost
        binding.btnGet.setOnClickListener {
            RetrofitClient.instance.registerUser("ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq", register)
                   .enqueue(object :
                       Callback<RegResponse> {
                       override fun onResponse(call: Call<RegResponse>, response: Response<RegResponse>
                       ) {
                           if (response.isSuccessful) {
                               Log.d("Ray", response.body()!!.success.toString())
                               Toast.makeText(
                                   this@MainActivity,
                                   "Successful ${response.body()}",
                                   Toast.LENGTH_SHORT
                               ).show()
                           } else {
                               Toast.makeText(this@MainActivity, getError(response), Toast.LENGTH_SHORT).show()
                               //RegResponse(success=null, message=Номер не существует)
                           }

                       }

                       override fun onFailure(call: Call<RegResponse>, t: Throwable) {
                           Toast.makeText(this@MainActivity, " 500 ОШИБКА ", Toast.LENGTH_SHORT).show()
                       }
                   })

            RetrofitClient.instance.checkOtp("ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq", otp)
                .enqueue(object : Callback<Token> {
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        if (response.isSuccessful) {
                            Log.d("Ray", response.body().toString())
                            refreshToken =  RefreshToken(response.body()?.refreshToken)

                            Toast.makeText(this@MainActivity, token, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                getError(response),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "response failure ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

        }

        binding.refreshToken.setOnClickListener {
            Log.d("Ray", token)
            RetrofitClient.instance.refreshToken("ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq", refreshToken)
                .enqueue(object : Callback<Token> {
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MainActivity, response.body()?.accessToken, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "adssad",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "response failure ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }


    }
}

private fun <T> getError(response: Response<T>): String {
    Log.d("Ray", response.toString())
    val gson = Gson()
    val type = object : TypeToken<RegResponse>() {}.type
    val errorResponse: RegResponse? = gson.fromJson(response.errorBody()?.charStream(), type)
    return errorResponse?.message.toString()
}


