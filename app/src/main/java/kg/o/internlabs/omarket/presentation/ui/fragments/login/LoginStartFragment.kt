package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginStartBinding
import kg.o.internlabs.omarket.utils.createCurrentNumber
import kg.o.internlabs.omarket.utils.delete996

@AndroidEntryPoint
class LoginStartFragment : BaseFragment<FragmentLoginStartBinding, LoginViewModel>(),
    NumberInputHelper {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private val prefs: StoragePreferences by lazy {
        StoragePreferences(requireContext())
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginStartBinding {
        return FragmentLoginStartBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        cusBtnEnter.buttonAvailability(false)
        cusBtnReg.buttonAvailability(0)
    }

    override fun initListener() = with(binding) {
        super.initListener()
        cusNum.setInterface(this@LoginStartFragment)
        cusBtnReg.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
        cusBtnEnter.setOnClickListener {
            val currentNumber = binding.cusNum.getVales().createCurrentNumber(cusNum. getVales())
            val numb = binding.cusNum.getVales().delete996(cusNum.getVales())
            Log.d("Ray", numb)
            if (currentNumber == prefs.userPhoneNumber) {
                findNavController().navigate(
                    LoginStartFragmentDirections
                        .goLoginEnd(numb)
                )
            } else {
                Toast.makeText(requireContext(), "Неверный номер телефона!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        binding.cusBtnEnter.buttonAvailability(notEmpty)
    }
}