package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.omarket.databinding.FragmentLoginStartBinding

@AndroidEntryPoint
class LoginStartFragment : BaseFragment<FragmentLoginStartBinding, LoginViewModel>(),
    NumberInputHelper {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginStartBinding {
        return FragmentLoginStartBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        cusBtnEnter.isEnabled = false
        cusBtnReg.isEnabled = false
    }

    override fun initListener() = with(binding) {
        super.initListener()
        cusNum.setInterface(this@LoginStartFragment)

        cusBtnReg.setOnClickListener {
            try {
                findNavController().navigate(LoginStartFragmentDirections
                    .goToRegistration(cusNum.getVales()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        cusBtnEnter.setOnClickListener {
            try {
                findNavController().navigate(
                    LoginStartFragmentDirections
                        .goLoginEnd(cusNum.getVales())
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        binding.cusBtnEnter.isEnabled = notEmpty
        binding.cusBtnReg.isEnabled = notEmpty
    }
}