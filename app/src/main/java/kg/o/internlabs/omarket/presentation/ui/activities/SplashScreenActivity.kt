package kg.o.internlabs.omarket.presentation.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.omarket.databinding.ActivitySplashScreenBinding
import kg.o.internlabs.omarket.presentation.ui.activities.activities.main_activity.MainActivity

@SuppressLint("CustomSplashScreen")
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

            // Start activity
            startActivity(Intent(this, MainActivity::class.java))

            // Animate the loading of new activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            // Close this activity
            finish()

        }, 4000)
    }

    private fun makeFullScreen() {
        // Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Make Fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Hide the toolbar
        supportActionBar?.hide()
    }
}