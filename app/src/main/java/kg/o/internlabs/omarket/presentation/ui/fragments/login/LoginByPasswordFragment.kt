package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginByPasswordBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.utils.InternetChecker
import kg.o.internlabs.omarket.utils.NetworkStatus
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

private typealias coreString = kg.o.internlabs.core.R.string

@AndroidEntryPoint
class LoginByPasswordFragment : BaseFragment<FragmentLoginByPasswordBinding, LoginViewModel>(),
    NumberInputHelper, PasswordInputHelper {

    private var hasInternet = false
    private var isNumberNotEmpty = false
    private var isPasswordNotEmpty = false
    private var args: LoginByPasswordFragmentArgs? = null

    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginByPasswordBinding {
        return FragmentLoginByPasswordBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = LoginByPasswordFragmentArgs.fromBundle(requireArguments())
    }

    override fun initView() = with(binding) {
        super.initView()
        // btn.buttonAvailability(false)
        args?.number?.let { cusNum.setValueToNumberField(it) }
        isNumberNotEmpty = cusNum.getValueFromNumberField().endsWith("X").not()
        complexWatcher()
    }

    override fun initListener() = with(binding) {
        super.initListener()
        cusNum.setInterface(this@LoginByPasswordFragment)
        cusPass.setInterface(this@LoginByPasswordFragment)
        cusPass.setMessage("")

        tbLoginEnd.setNavigationOnClickListener { findNavController().navigateUp() }

        btn.setOnClickListener {
            if (hasInternet) {
                viewModel.loginUser(
                    RegisterEntity(
                        msisdn = viewModel.formattedValues(cusNum.getValueFromNumberField()),
                        password = cusPass.getValueFromPasswordField()
                    )
                )
                initObserver()
            } else {
                noConnectionState()
            }
        }

        btnPdf.setOnClickListener {
            findNavController().navigate(R.id.pdfFragment)
        }
    }

    private fun noConnectionState() {
        if (isTokenExists().not()) {
            try {
                requireActivity().makeToast(getString(coreString.user_not_found))
                findNavController().navigate(
                    LoginByPasswordFragmentDirections
                        .goToRegistrationFragment(binding.cusNum.getValueFromNumberField())
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return
        }

        try {
            findNavController().navigate(R.id.mainFragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isTokenExists(): Boolean {
        viewModel.getTokenState()
        var tokenExists = false
        safeFlowGather {
            viewModel.isTokenSavedState.collectLatest {
                tokenExists = it
            }
        }
        return tokenExists
    }

    override fun checkInternet() {
        super.checkInternet()
        InternetChecker(requireContext()).observe(requireActivity()) {
            when (it) {
                NetworkStatus.Available -> {
                    try {
                        hasInternet = true
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                NetworkStatus.Unavailable -> {
                    hasInternet = false
                }
            }
        }
    }

    private fun initObserver() = with(binding) {
        this@LoginByPasswordFragment.safeFlowGather {
            viewModel.movieState.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        viewModel.saveNumberToPrefs(cusNum.getValueFromNumberField())
                        // btn.buttonFinished()
                        progressBar.isVisible = false
                        try {
                            findNavController().navigate(R.id.mainFragment)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    is ApiState.Failure -> {
                        // btn.buttonFinished()
                        progressBar.isVisible = false
                        it.msg.message?.let { it1 ->
                            // btn.buttonAvailability(false)
                            // btn.isEnabled = false

                            when (it1) {
                                getString(R.string.time_out) -> {
                                    if (!hasInternet) {
                                        noConnectionState()
                                    }
                                }
                                getString(R.string.incorrect_number) -> {
                                    cusNum.setErrorMessage(it1)
                                }
                                else -> {
                                    cusNum.setMessage(resources.getString(coreString.enter_number))
                                    cusPass.setErrorMessage(it1)
                                }
                            }
                        }
                    }
                    is ApiState.Loading -> {
                        btn.isVisible = false
                        progressBar.isVisible = true
                        // btn.buttonActivated()

                    }
                }
            }
        }
    }

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        isNumberNotEmpty = notEmpty
        complexWatcher()
    }

    override fun passwordWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        isPasswordNotEmpty = notEmpty
        binding.cusPass.setMessage("")
        complexWatcher()
    }

    private fun complexWatcher() = with(binding) {
        if (isNumberNotEmpty.and(isPasswordNotEmpty)) {
            btn.isClickable = true
            btn.isEnabled = true
        } else {
            btn.isClickable = false
            btn.isEnabled = false
        }
    }
}
