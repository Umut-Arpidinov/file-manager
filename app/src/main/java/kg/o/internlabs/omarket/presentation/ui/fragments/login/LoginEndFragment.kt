package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.databinding.FragmentLoginEndBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginEndFragment : BaseFragment<FragmentLoginEndBinding, LoginViewModel>(),
    NumberInputHelper, PasswordInputHelper {

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

    override fun initView() {
        super.initView()
        println(args?.number)

    }

    override fun initListener() = with(binding) {
        super.initListener()
        // setting watchers
        cusNum.setInterface(this@LoginEndFragment)
        cusPass.setInterface(this@LoginEndFragment)
        btn.buttonAvailability(false)
    override fun initViewModel() {
        super.initViewModel()
        initObserver()
    }


    private fun initObserver() {
            safeFlowGather {
                viewModel.movieState.collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            Log.d("Ray", it.data.toString())
                        }
                        is ApiState.Failure -> {
                            Log.d("Ray", it.msg.toString())

                        }
                        ApiState.Loading -> {
                            Log.d("Ray", "Loading ")
                        }
                    }
                }
            }
        }

        fun safeFlowGather(action: suspend () -> Unit) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    action()
                }
            }
        }

        override fun initListener() = with(binding) {
            super.initListener()
            // setting watchers

            cusNum.setInterface(this@LoginEndFragment)
            cusPass.setInterface(this@LoginEndFragment)

        btn.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
        btnPdf.setOnClickListener {
            findNavController().navigate(R.id.pdfFragment)
        }
    }
            val reg = RegisterEntity(msisdn = "996702270242", password = "1234567890")
            viewModel.loginUser(reg)
        }

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        isNumberNotEmpty = notEmpty
        complexWatcher()


//        val navHostFragment = requireActivity().supportFragmentManager
//            .findFragmentById(R.id.nav_host) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController.navigate(R.id.mainFragment)
    }

    override fun passwordWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        isPasswordNotEmpty = notEmpty
        complexWatcher()
    }

    // следить за двумя полями одновременно


    fun complexWatcher() = with(binding) {
        println("num ------" + isNumberNotEmpty)
        println("pass ------" + isPasswordNotEmpty)
        println("--------------------------")
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

    // TODO чтобы получить значение номера телефона вызыаем геттер так binding.cusNum.getValues
    // TODO чтобы получить значение пороля вызыаем геттер так binding.cusPass.getPasswordField()
}