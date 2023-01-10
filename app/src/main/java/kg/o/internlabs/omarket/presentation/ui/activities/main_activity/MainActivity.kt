package kg.o.internlabs.omarket.presentation.ui.activities.main_activity

import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseActivity
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.ActivityMainBinding
import kg.o.internlabs.omarket.presentation.ui.fragments.login.LoginStartFragment
import kg.o.internlabs.omarket.presentation.ui.fragments.main.MainFragment
import kg.o.internlabs.omarket.utils.lightStatusBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    private lateinit var navController: NavController


    override val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        ActivityMainBinding.inflate(inflater)

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host)
        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                when (fragment) {
                    is LoginStartFragment, is MainFragment -> finish()
                    else -> onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        lightStatusBar(window,true)
        viewModel.isTokenExpired()
        checkToken()
    }

    private fun checkToken() {
        safeFlowGather {
            viewModel.accessToken.collectLatest {
                setupNavigation(it)
            }
        }
    }

    private fun safeFlowGather(action: suspend () -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                action()
            }
        }
    }

    override fun onDestroy() {
        println("main on destroy----")
        // TODO временная мера не удалять viewModel.saveLoginStatusToPrefs(false)
        super.onDestroy()
    }

    private fun setupNavigation(s: String) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.main_graph)
        when(s) {
            "403" -> {
                navGraph.setStartDestination(R.id.loginStartFragment)
            }
            "200" -> {
                navGraph.setStartDestination(R.id.mainFragment)
            }
        }
        navController.graph = navGraph
    }
}