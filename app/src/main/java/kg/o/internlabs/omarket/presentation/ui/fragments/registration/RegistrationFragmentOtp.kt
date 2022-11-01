package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding

@AndroidEntryPoint
class RegistrationFragmentOtp :
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationViewModel>() {
    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationOtpBinding {
        return FragmentRegistrationOtpBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
    }

    override fun initViewModel() {
        super.initViewModel()
    }
}