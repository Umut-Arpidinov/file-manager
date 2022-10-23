package kg.o.internlabs.omarket.presentation.ui.activities.activities

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.core.model.Register
import kg.o.internlabs.omarket.databinding.ActivityMainBinding
import kg.o.internlabs.omarket.presentation.viewmodels.activitiesviewmodels.MainViewModel

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
    }


}