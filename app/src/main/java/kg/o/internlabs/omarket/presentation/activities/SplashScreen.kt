package kg.o.internlabs.omarket.presentation.activities

import android.view.LayoutInflater
import kg.o.internlabs.core.BaseActivity
import kg.o.internlabs.core.BaseViewModel
import kg.o.internlabs.omarket.databinding.ActivitySplashScreenBinding

class SplashScreen : BaseActivity<BaseViewModel, ActivitySplashScreenBinding>() {

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(inflater)
    }
}