package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentRegistrationBinding

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

    override fun initListener() = with(binding){
        super.initListener()
        // setting watchers
        cusNum.setInterface(this@RegistrationFragment)
        cusPass1.setInterface(this@RegistrationFragment)
        cusPass1.setInterface(this@RegistrationFragment, 1)
    }


    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        isNumberNotEmpty = notEmpty
        complexWatcher()
    }

    override fun passwordWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        when(fieldsNumber) {
            0 -> isFirstPasswordNotEmpty = notEmpty
            1 -> isSecondPasswordNotEmpty = notEmpty
        }
        complexWatcher()
    }

    // следить за тремья полями одновременно
    private fun complexWatcher() = with(binding){
        println("num ------"+isNumberNotEmpty)
        println("pass1 ------"+isFirstPasswordNotEmpty)
        println("pass2 ------"+isSecondPasswordNotEmpty)
        println("--------------------------")
        // TODO Здесь можно управлять кнопкой если isNumberNotEmpty &&
    //TODO   isFirstPasswordNotEmpty && isSecondPasswordNotEmpty true то...
        // TODO так можно переключать кнопку
        //btnSendOtp.buttonAvailability(isNumberNotEmpty && isFirstPasswordNotEmpty && isSecondPasswordNotEmpty)
    }

    // TODO чтобы получить значение номера телефона вызыаем геттер так binding.cusNum.getValues
    // TODO значение 1 поле пороля  вызыаем геттер так binding.cusPass1.getPasswordField()
    // TODO значение 2 поле пороля  вызыаем геттер так binding.cusPass2.getPasswordField()
}