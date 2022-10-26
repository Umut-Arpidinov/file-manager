package kg.o.internlabs.omarket.presentation.ui.activities.activities

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.core.model.Otp
import kg.o.internlabs.core.model.Register
import kg.o.internlabs.core.model.Token
import kg.o.internlabs.core.network.RetrofitClient
import kg.o.internlabs.omarket.databinding.ActivityMainBinding
import kg.o.internlabs.omarket.presentation.viewmodels.activitiesviewmodels.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val register = Register("996700517119", "1234", "1234")
    private val otp = Otp("996500997007","7197")

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        //binding.navHost
        binding.btnGet.setOnClickListener{
           /* RetrofitClient.instance.registerUser("ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq",register).enqueue(object :
                Callback<Otp> {
                override fun onResponse(call: Call<Otp>, response: Response<Otp>) {
                    if (response.isSuccessful) {
                        Log.d("Ray", response.body()!!.otp.toString())
                    }
                    Toast.makeText(this@MainActivity, "response fail ${response.message()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Otp>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            }) */

            RetrofitClient.instance.checkOtp("ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq", otp).enqueue(object: Callback<Token>{
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful) {
                        Log.d("Ray", response.body().toString())
                    }
                    Toast.makeText(this@MainActivity, "response fail ${response.message()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "response failure ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }


}