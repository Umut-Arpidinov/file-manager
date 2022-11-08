package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginEndBinding

@AndroidEntryPoint
class LoginEndFragment : BaseFragment<FragmentLoginEndBinding, LoginViewModel>(),
    NumberInputHelper, PasswordInputHelper {
    private var isNumberNotEmpty = false
    private var isPasswordNotEmpty = false

    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginEndBinding {
        return FragmentLoginEndBinding.inflate(inflater)
    }

    override fun initListener() = with(binding) {
        super.initListener()
        // setting watchers
        cusNum.setInterface(this@LoginEndFragment)
        cusPass.setInterface(this@LoginEndFragment)
        btn.buttonAvailability(false)

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

        if (isNumberNotEmpty&&isPasswordNotEmpty){
            btn.buttonAvailability(true)
        } else {
            btn.buttonAvailability(false)
            cusPass.setErrorMessage(getString(kg.o.internlabs.core.R.string.incorrect_password))
            forgotPassword.visibility = View.VISIBLE
        }
        println(cusNum.getVales() + " number")
        println(cusPass.getPasswordField() + " password")

        println("num ------" + isNumberNotEmpty)
        println("pass ------" + isPasswordNotEmpty)
        println("--------------------------")
        // TODO Здесь можно управлять кнопкой если isNumberNotEmpty && isPasswordNotEmpty true то...
        // TODO так можно переключать кнопку
        //btnSendOtp.buttonAvailability(isNumberNotEmpty && isPasswordNotEmpty)
        btnPdf.setOnClickListener {
            findNavController().navigate(R.id.pdfFragment)
        }
    }

    // TODO чтобы получить значение номера телефона вызыаем геттер так binding.cusNum.getValues
    // TODO чтобы получить значение пороля вызыаем геттер так binding.cusPass.getPasswordField()
}