package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginEndBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.utils.createCurrentNumber
import kg.o.internlabs.omarket.utils.getErrorMessage
import kg.o.internlabs.omarket.utils.makeToast
import kotlinx.coroutines.flow.collectLatest



@AndroidEntryPoint
class LoginEndFragment : BaseFragment<FragmentLoginEndBinding, LoginViewModel>(),
    NumberInputHelper, PasswordInputHelper {

    private var isNumberNotEmpty = false
    private var isPasswordNotEmpty = false
    private var args: LoginEndFragmentArgs? = null

    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    private val prefs: StoragePreferences by lazy {
        StoragePreferences(requireContext())
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginEndBinding {
        return FragmentLoginEndBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            args = LoginEndFragmentArgs.fromBundle(it)

        }
    }

    override fun initView() {
        super.initView()
        binding.cusNum.setText(args?.number.toString())
    }

    private fun initObserver(number: String, password: String) {

        val currentNumber = number.createCurrentNumber(number)

        val reg = RegisterEntity(msisdn = currentNumber, password = password)
        viewModel.loginUser(reg)
        safeFlowGather {
            viewModel.movieState.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        findNavController().navigate(R.id.mainFragment)
                        prefs.token = it.data.accessToken
                        prefs.refreshToken = it.data.refreshToken
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
        // setting watchers

        cusNum.setInterface(this@LoginEndFragment)
        cusPass.setInterface(this@LoginEndFragment)

        cusNum.setInterface(this@LoginEndFragment)
        cusPass.setInterface(this@LoginEndFragment)
        btn.buttonAvailability(false)

        btn.setOnClickListener {
            initObserver(binding.cusNum.getVales(), binding.cusPass.getPasswordField())

        }
        btnPdf.setOnClickListener {
            findNavController().navigate(R.id.pdfFragment)
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

    // следить за двумя полями одновременно
    private fun complexWatcher() = with(binding) {

        if (isNumberNotEmpty && isPasswordNotEmpty) {
            btn.buttonAvailability(true)
        } else {
            btn.buttonAvailability(false)
            // cusPass.setErrorMessage(getString(kg.o.internlabs.core.R.string.incorrect_password))
        }

        // TODO Здесь можно управлять кнопкой если isNumberNotEmpty && isPasswordNotEmpty true то...
        // TODO так можно переключать кнопку
        //btnSendOtp.buttonAvailability(isNumberNotEmpty && isPasswordNotEmpty)
    }


}