package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import kg.o.internlabs.core.BaseFragment
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginProgressStateBinding
import kg.o.internlabs.omarket.presentation.viewmodels.fragmentsviewmodels.LoginViewModel

class LoginFragmentProgressState : BaseFragment<FragmentLoginProgressStateBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginProgressStateBinding {
         return FragmentLoginProgressStateBinding.inflate(inflater)
    }

    override fun initListener() {
        super.initListener()
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.loginFragmentFinalState)
    }
}