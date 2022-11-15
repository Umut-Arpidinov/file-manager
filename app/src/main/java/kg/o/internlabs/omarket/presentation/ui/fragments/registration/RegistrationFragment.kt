package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentRegistrationBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

    private fun safeFlowGather(action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                action()
            }
        }
    }

    private fun initObserver() = with(binding){
        safeFlowGather {
            viewModel.registerUser.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        btnSendOtp.buttonFinished()
                        goNextPage()
                    }
                    is ApiState.Failure -> {
                        btnSendOtp.buttonFinished()
                        btnSendOtp.buttonAvailability(false)
                        it.msg.message?.let { it1 ->
                            when(it1) {
                                getString(R.string.time_out) -> {
                                    // TODO  snack bar
                                }
                                getString(R.string.incorrect_number) -> {
                                    cusNum.setHintText(it1)
                                }
                                else -> {
                                    cusPass.setErrorMessage(it1)
                                    cusPass1.setErrorMessage(it1)
                                }
                            }
                        }
                    }
                    ApiState.Loading -> {
                        btnSendOtp.buttonActivated()
                    }
                }
            }
        }
    }

    private fun goNextPage() {
        findNavController().navigate(
            RegistrationFragmentDirections
                .goToOtp(binding.cusNum.getVales(), binding.cusPass.getPasswordField())
        )
    }

    override fun initListener() = with(binding) {
        super.initListener()
        cusNum.setInterface(this@RegistrationFragment)
        cusPass.setInterface(this@RegistrationFragment)
        cusPass1.setInterface(this@RegistrationFragment, 1)

        cusPass.setMessage(getString(kg.o.internlabs.core.R.string.helper_text_create_password))
        btnSendOtp.buttonAvailability(false)

        btnSendOtp.setOnClickListener {
            viewModel.registerUser(
                RegisterEntity(
                    msisdn = viewModel.formattedValues(cusNum.getVales()),
                    password = cusPass.getPasswordField(),
                    password2 = cusPass.getPasswordField()
                )
            )
            initObserver()
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