package kg.o.internlabs.omarket.presentation.ui.activities.activities

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import kg.o.internlabs.core.BaseActivity
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.ActivityMainBinding
import kg.o.internlabs.omarket.presentation.viewmodels.activitiesviewmodels.MainViewModel


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initView() {

    }

    override fun onDestroy() {
        println("onDestroy")
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        println("pause")
    }

    override fun onStop() {
        super.onStop()
        println("stop")
    }
}