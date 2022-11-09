package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.OtpHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.omarket.databinding.FragmentLoginEndBinding
import kg.o.internlabs.omarket.R

@AndroidEntryPoint
class LoginEndFragment : BaseFragment<FragmentLoginEndBinding, LoginViewModel>(),
    NumberInputHelper, PasswordInputHelper, OtpHelper  {

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

    override fun initListener() = with(binding){
        super.initListener()

        binding.otp.setInterface(this@LoginEndFragment)

        binding.btnOtp.setOnClickListener {
            println( binding.otp.getValues())
            binding.otp.setError("Niene")
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
    private fun complexWatcher() = with(binding){
        println("num ------"+isNumberNotEmpty)
        println("pass ------"+isPasswordNotEmpty)
        println("--------------------------")
        // TODO Здесь можно управлять кнопкой если isNumberNotEmpty && isPasswordNotEmpty true то...
        // TODO так можно переключать кнопку
        //btnSendOtp.buttonAvailability(isNumberNotEmpty && isPasswordNotEmpty)
    }

    // TODO чтобы получить значение номера телефона вызыаем геттер так binding.cusNum.getValues
    // TODO чтобы получить значение пороля вызыаем геттер так binding.cusPass.getPasswordField()

    override fun sendOtpAgain() {
        println()
        println("Would you not mind to send me, the otp one more time?")
        Toast.makeText(requireContext(), "Would you not mind to send me, the otp one more time?",
        Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.mainFragment)
    }

    override fun watcher(notEmpty: Boolean) {
        println("--jj------$notEmpty")
    }

}