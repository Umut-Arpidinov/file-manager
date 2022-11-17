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
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

private typealias coreString = kg.o.internlabs.core.R.string

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
    }

    private fun observe() {
        safeFlowGather {
            viewModel.num.take(1).collect {
                if (it) {
                    try {
                        navigateOk(binding.cusNum.getVales())
                    } catch (e : IllegalArgumentException){
                        println("hhhhhhh "+e.printStackTrace())
                    }
                } else {
                    binding.cusNum.setErrorMessage(resources.getString(coreString.number_mistake))
                }
            }
        }
    }

    private fun navigateOk(vales: String) {
    findNavController().navigate(
            LoginStartFragmentDirections
                .goLoginEnd(vales)
        )
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
            observe()
        }
    }

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        binding.cusBtnEnter.isEnabled = notEmpty
    }
}