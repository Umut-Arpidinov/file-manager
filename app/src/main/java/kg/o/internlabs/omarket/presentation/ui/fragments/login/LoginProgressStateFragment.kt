package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginProgressStateBinding

class LoginProgressStateFragment : BaseFragment<FragmentLoginProgressStateBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginProgressStateBinding {
         return FragmentLoginProgressStateBinding.inflate(inflater)
    }

    override fun initListener() {
        super.initListener()
        findNavController().navigate(R.id.loginFragmentFinalState)
    }
}