package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentLoginFinalStateBinding

@AndroidEntryPoint
class LoginFinalStateFragment : BaseFragment<FragmentLoginFinalStateBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginFinalStateBinding {
         return FragmentLoginFinalStateBinding.inflate(inflater)
    }

    override fun initListener() {
        super.initListener()

//        val navHostFragment = requireActivity().supportFragmentManager
//            .findFragmentById(R.id.nav_host) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController.navigate(R.id.mainFragment)
    }
}