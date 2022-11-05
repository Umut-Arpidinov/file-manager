package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginEndBinding

@AndroidEntryPoint
class LoginEndFragment : BaseFragment<FragmentLoginEndBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginEndBinding {
         return FragmentLoginEndBinding.inflate(inflater)
    }

    override fun initListener() {
        super.initListener()
        findNavController().navigate(R.id.registrationOtpFragment)
    }
}