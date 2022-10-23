package kg.o.internlabs.omarket.presentation.ui.fragments

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentLoginBinding
import kg.o.internlabs.omarket.presentation.viewmodels.fragmentsviewmodels.LoginViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginBinding {
         return FragmentLoginBinding.inflate(inflater)
    }
}