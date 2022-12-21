package kg.o.internlabs.omarket.presentation.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.omarket.databinding.ActivitySplashScreenBinding
import kg.o.internlabs.omarket.presentation.ui.activities.main_activity.ClosingService
import kg.o.internlabs.omarket.presentation.ui.activities.main_activity.MainActivity
import kg.o.internlabs.omarket.presentation.ui.activities.main_activity.MainActivityViewModel

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<MainActivityViewModel, ActivitySplashScreenBinding>() {

    override val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(inflater)
    }

    override fun operationBeforeSetContent() {
        super.operationBeforeSetContent()
        makeFullScreen()
    }

    override fun initView() {
        super.initView()
        viewModel.isTokenExpired()
        startService(Intent(baseContext, ClosingService::class.java))
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 4000)
    }

    private fun makeFullScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}