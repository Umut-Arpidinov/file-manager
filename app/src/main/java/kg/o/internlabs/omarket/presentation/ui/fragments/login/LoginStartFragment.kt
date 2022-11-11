package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginStartBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        cusBtnEnter.buttonAvailability(false)
        cusBtnReg.buttonAvailability(0)
    }

    override fun initViewModel() {
        super.initViewModel()
        observe()
    }

    private fun observe() {
        safeFlowGather {
            viewModel.ate.collectLatest {
                if (it) {
                    findNavController().navigate(
                        LoginStartFragmentDirections
                            .goLoginEnd(binding.cusNum.getVales())
                    )
                } else {
                    println("no00000000000000000000")
                }
            }
        }
    }

    private fun safeFlowGather(action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                action()
            }
        }
    }

    override fun initListener() = with(binding) {
        super.initListener()
        cusNum.setInterface(this@LoginStartFragment)
        cusBtnReg.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
        cusBtnEnter.setOnClickListener {
            viewModel.checkNumber(cusNum.getVales())
        }
    }

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        binding.cusBtnEnter.buttonAvailability(notEmpty)
    }
}