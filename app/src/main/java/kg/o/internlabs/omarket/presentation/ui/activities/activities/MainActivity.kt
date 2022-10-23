package kg.o.internlabs.omarket.presentation.ui.activities.activities

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.core.model.Register
import kg.o.internlabs.core.network.RetrofitClient
import kg.o.internlabs.omarket.databinding.ActivityMainBinding
import kg.o.internlabs.omarket.presentation.viewmodels.activitiesviewmodels.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val register = Register("996703887499", "1234", "1234")

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
            RetrofitClient.instance.registerUser("ypsGjpmpDR9GndA9YvzBHpIqXsFStfyq",register).enqueue(object :
                Callback<Register> {
                override fun onResponse(call: Call<Register>, response: Response<Register>) {
                    if (response.isSuccessful) {
                        println("call = " + call + ", response = " + response.body())
                        Toast.makeText(this@MainActivity, "Yo", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(this@MainActivity, "response fail ${response.message()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Register>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "failure", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


}