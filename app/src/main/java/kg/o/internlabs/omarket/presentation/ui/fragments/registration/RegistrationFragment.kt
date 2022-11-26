package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.os.Bundle
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
import kg.o.internlabs.core.custom_views.CustomSnackbar
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentRegistrationBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private typealias coreString = kg.o.internlabs.core.R.string


@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding,
        RegistrationViewModel>(), NumberInputHelper, PasswordInputHelper {

    private var isNumberNotEmpty = false
    private var isFirstPasswordNotEmpty = false
    private var isSecondPasswordNotEmpty = false

    private var args: RegistrationFragmentArgs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = RegistrationFragmentArgs.fromBundle(requireArguments())
    }

    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        args?.number?.let { cusNum.setValue(it) }
        isNumberNotEmpty = cusNum.getVales().endsWith("X").not()
        complexWatcher()

        cusPass.setMessage(getString(coreString.helper_text_create_password))
    }

    private fun safeFlowGather(action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                action()
            }
        }
    }

    private fun initObserver() = with(binding) {
        safeFlowGather {
            viewModel.registerUser.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        cusNum.setMessage(resources.getString(coreString.enter_number))
//                        btnSendOtp.buttonFinished()
                        processFinished()
                        try {
                            goNextPage()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    is ApiState.Failure -> {
//                        btnSendOtp.buttonFinished()
//                        btnSendOtp.buttonAvailability(false)
                        processFinished()
                        buttonAvailable(false)
                        it.msg.message?.let { it1 ->
                            when (it1) {
                                getString(R.string.time_out) -> {
                                    val customSnackbar = CustomSnackbar(requireActivity())
                                    customSnackbar.snackButtonError(
                                        getString(R.string.no_connection), root)
                                }
                                getString(R.string.incorrect_number),
                                getString(R.string.number_exists) -> {
                                    cusNum.setErrorMessage(it1)
                                }
                                else -> {
                                    cusPass.setErrorMessage(it1)
                                    cusPass1.setErrorMessage(it1)
                                }
                            }
                        }
                    }
                    ApiState.Loading -> {
//                        btnSendOtp.buttonActivated()
                        processStarted()
                    }
                }
            }
        }
    }

    private fun goNextPage() {
        try {
            findNavController().navigate(
                RegistrationFragmentDirections
                    .goToOtp(binding.cusNum.getVales())
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initListener() = with(binding) {
        super.initListener()
        cusNum.setInterface(this@RegistrationFragment)
        cusPass.setInterface(this@RegistrationFragment)
        cusPass1.setInterface(this@RegistrationFragment, 1)
        tbRegistration.setNavigationOnClickListener { findNavController().navigateUp() }

//        btnSendOtp.buttonAvailability(false)
        buttonAvailable(false)

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
            0 -> {
                isFirstPasswordNotEmpty = notEmpty
            }
            1 -> {
                isSecondPasswordNotEmpty = notEmpty
            }
        }
        complexWatcher()
    }

    private fun complexWatcher() = with(binding) {
        if (isNumberNotEmpty.and(isFirstPasswordNotEmpty).and(isSecondPasswordNotEmpty)) {
            if (cusPass.getPasswordField() == cusPass1.getPasswordField()) {
//                btnSendOtp.buttonAvailability(true)
                buttonAvailable(true)
                textButton.visibility = View.VISIBLE
                textButton.movementMethod = LinkMovementMethod.getInstance()
                cusPass.setMessage(getString(coreString.helper_text_create_password))
                cusPass1.setMessage("")
            }
            if(cusPass.getPasswordField() != cusPass1.getPasswordField()){
//                btnSendOtp.buttonAvailability(false)
                buttonAvailable(false)
                textButton.visibility = View.GONE
                cusPass1.setErrorMessage(getString(coreString.password_not_match))
                cusPass.setErrorMessage("")
            }

        } else {
//            btnSendOtp.buttonAvailability(false)
            buttonAvailable(false)
            textButton.visibility = View.GONE
            cusPass1.setMessage("")
        }
    }

    private fun processFinished() = with(binding) {
        progressBar.visibility = View.GONE
        btnSendOtp.visibility = View.VISIBLE
    }

    private fun processStarted() = with(binding) {
        btnSendOtp.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun buttonAvailable(state: Boolean) = with(binding) {
        if (state) {
            btnSendOtp.isClickable = true
            btnSendOtp.isEnabled = true
        } else {
            btnSendOtp.isClickable = false
            btnSendOtp.isEnabled = false
        }
    }
}