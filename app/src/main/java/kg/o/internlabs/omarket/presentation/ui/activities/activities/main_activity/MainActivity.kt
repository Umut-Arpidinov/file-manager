package kg.o.internlabs.omarket.presentation.ui.activities.activities.main_activity

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.ActivityMainBinding
import kg.o.internlabs.omarket.presentation.ui.fragments.login.LoginStartFragment
import kg.o.internlabs.omarket.presentation.ui.fragments.main.MainFragment


@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    override val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host)
        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                when(fragment) {
                    is LoginStartFragment, is MainFragment -> finish()
                    else -> onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.statusListener()
    }

    override fun onDestroy() {
        println("main on destroy----")
        viewModel.saveLoginStatusToPrefs(false)
        super.onDestroy()
    }
}


