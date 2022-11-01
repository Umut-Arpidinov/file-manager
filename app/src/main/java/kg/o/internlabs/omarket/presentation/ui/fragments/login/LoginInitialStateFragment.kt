package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginInitialStateBinding

@AndroidEntryPoint
class LoginInitialStateFragment : BaseFragment<FragmentLoginInitialStateBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginInitialStateBinding {
         return FragmentLoginInitialStateBinding.inflate(inflater)
    }

    override fun initListener() {
        super.initListener()
        findNavController().navigate(R.id.loginFragmentProgressState)
    }
}