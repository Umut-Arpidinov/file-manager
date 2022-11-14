package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.omarket.databinding.FragmentRegistrationBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.utils.createCurrentNumber
import kg.o.internlabs.omarket.utils.getErrorMessage
import kg.o.internlabs.omarket.utils.makeToast
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding,
        RegistrationViewModel>(), NumberInputHelper, PasswordInputHelper {

    private var isNumberNotEmpty = false
    private var isFirstPasswordNotEmpty = false
    private var isSecondPasswordNotEmpty = false

    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater)
    }

    private fun initObserver(password1: String, password2: String) {
        safeFlowGather {

        val reg = RegisterEntity(msisdn = binding.cusNum.getVales().createCurrentNumber(binding.cusNum.getVales()), password = password1, password2 = password2)
        viewModel.registerUser(reg)
            viewModel.registerUser.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        findNavController().navigate(RegistrationFragmentDirections
                            .goToOtp(reg.msisdn.toString(), password2))
                    }
                    is ApiState.Failure -> {

                    }
                    is ApiState.FailureError ->{
                        requireActivity().makeToast(it.msg.getErrorMessage())
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    override fun initListener() = with(binding) {
        super.initListener()
        cusNum.setInterface(this@RegistrationFragment)
        cusPass.setInterface(this@RegistrationFragment)
        cusPass1.setInterface(this@RegistrationFragment, 1)
        cusPass.setMessage(getString(kg.o.internlabs.core.R.string.helper_text_create_password))
        btnSendOtp.buttonAvailability(false)

        btnSendOtp.setOnClickListener {
            initObserver(binding.cusPass.getPasswordField(), binding.cusPass1.getPasswordField())
        }
    }

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        isNumberNotEmpty = notEmpty
        complexWatcher()
    }

    override fun passwordWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        when (fieldsNumber) {
            0 -> isFirstPasswordNotEmpty = notEmpty
            1 -> isSecondPasswordNotEmpty = notEmpty
        }
        complexWatcher()
    }

    // следить за тремья полями одновременно
    private fun complexWatcher() = with(binding) {
        if (isNumberNotEmpty && isFirstPasswordNotEmpty && isSecondPasswordNotEmpty) {
            if (cusPass.getPasswordField() == cusPass1.getPasswordField()) {
                btnSendOtp.buttonAvailability(true)
                textButton.visibility = View.VISIBLE
                textButton.movementMethod = LinkMovementMethod.getInstance()
                cusPass.setMessage(getString(kg.o.internlabs.core.R.string.helper_text_create_password))
                cusPass1.setMessage("")
            } else {
                btnSendOtp.buttonAvailability(false)
                textButton.visibility = View.GONE
                cusPass1.setErrorMessage(getString(kg.o.internlabs.core.R.string.password_not_match))
                cusPass.setErrorMessage("")
            }
        } else {
            btnSendOtp.buttonAvailability(false)
            textButton.visibility = View.GONE
            cusPass.setMessage(getString(kg.o.internlabs.core.R.string.helper_text_create_password))
            cusPass1.setMessage("")
        }
    }
}