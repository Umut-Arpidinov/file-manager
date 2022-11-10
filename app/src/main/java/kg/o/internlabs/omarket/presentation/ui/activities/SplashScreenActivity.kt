package kg.o.internlabs.omarket.presentation.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.WindowInsets
import android.view.WindowManager
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.omarket.databinding.ActivitySplashScreenBinding
import kg.o.internlabs.omarket.presentation.ui.activities.activities.main_activity.MainActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<BaseViewModel, ActivitySplashScreenBinding>() {

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(inflater)
    }

    override fun operationBeforeSetContent() {
        super.operationBeforeSetContent()
        makeFullScreen()
    }

    override fun initView() {
        super.initView()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 4000)
    }

    @Suppress("DEPRECATION")
    private fun makeFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}