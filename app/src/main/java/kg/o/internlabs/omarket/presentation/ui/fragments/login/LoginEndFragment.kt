package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
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
import kg.o.internlabs.omarket.databinding.FragmentLoginEndBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.utils.InternetChecker
import kg.o.internlabs.omarket.utils.NetworkStatus
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

private typealias coreStringRes = kg.o.internlabs.core.R.string

@AndroidEntryPoint
class LoginEndFragment : BaseFragment<FragmentLoginEndBinding, LoginViewModel>(),
    NumberInputHelper, PasswordInputHelper {

    private var hasInternet = false
    private var isNumberNotEmpty = false
    private var isPasswordNotEmpty = false
    private var args: LoginEndFragmentArgs? = null

    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginEndBinding {
        return FragmentLoginEndBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = LoginEndFragmentArgs.fromBundle(requireArguments())
    }

    override fun initView() = with(binding) {
        super.initView()
        btn.buttonAvailability(false)
        if (args != null) {
            if (!args!!.number.isNullOrEmpty()) {
                cusNum.setValue(args!!.number!!)
                if (!cusNum.getVales().endsWith("X")) {
                    isNumberNotEmpty = true
                    complexWatcher()
                }
            }
        }
    }

    override fun initListener() = with(binding) {
        super.initListener()
        cusNum.setInterface(this@LoginEndFragment)
        cusPass.setInterface(this@LoginEndFragment)

        tbLoginEnd.setNavigationOnClickListener { findNavController().navigateUp() }

        btn.setOnClickListener {
            if (hasInternet) {
                viewModel.loginUser(
                    RegisterEntity(
                        msisdn = viewModel.formattedValues(cusNum.getVales()),
                        password = cusPass.getPasswordField()
                    )
                )
                initObserver()
            }
            else {
                viewModel.checkPassword(cusPass.getPasswordField())
                loadLocalState()
            }
        }
    }

    private fun loadLocalState() {
        safeFlowGather {
            viewModel.pwd.take(1).collect {
                if (it) {
                    findNavController().navigate(R.id.mainFragment)
                }
                else {
                    with(binding) {
                        btn.buttonAvailability(false)
                        cusPass.setErrorMessage(resources.getString(coreStringRes.incorrect_password))
                    }
                }
            }
        }
    }

    override fun checkInternet() {
        super.checkInternet()
        InternetChecker(requireContext()).observe(requireActivity()) {
            when (it) {
                NetworkStatus.Available -> {
                    try {
                        hasInternet = true
                    } catch (_: Exception) {
                    }
                }
                NetworkStatus.Unavailable -> {
                    hasInternet = false
                }
            }
        }
    }

    private fun initObserver() = with(binding){
        safeFlowGather {
            viewModel.movieState.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        viewModel.putNumber(viewModel.formattedValues(cusNum.getVales()))
                        viewModel.putPwd(cusPass.getPasswordField())
                        btn.buttonFinished()
                        findNavController().navigate(R.id.mainFragment)
                    }
                    is ApiState.Failure -> {
                        btn.buttonFinished()
                        it.msg.message?.let { it1 ->
                            btn.buttonAvailability(false)
                            when(it1) {
                                getString(R.string.time_out) -> {
                                    if (!hasInternet) {
                                        loadLocalState()
                                    }
                                }
                                getString(R.string.incorrect_number) -> {
                                    cusNum.setErrorMessage(it1)
                                }
                                else -> {
                                    cusPass.setErrorMessage(it1)
                                }
                            }
                        }
                    }
                    is ApiState.Loading -> {
                        btn.buttonActivated()
                    }
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

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        isNumberNotEmpty = notEmpty
        complexWatcher()
    }

    override fun passwordWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        isPasswordNotEmpty = notEmpty
        complexWatcher()
    }

    private fun complexWatcher() = with(binding) {
        if (isNumberNotEmpty && isPasswordNotEmpty) {
            btn.buttonAvailability(true)
        } else {
            btn.buttonAvailability(false)
        }
    }
}