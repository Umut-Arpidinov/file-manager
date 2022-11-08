package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.core.custom_views.PasswordInputHelper
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
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

    override fun initListener() = with(binding) {
        super.initListener()
        // setting watchers
        cusNum.setInterface(this@RegistrationFragment)
        cusPass.setInterface(this@RegistrationFragment)
        cusPass1.setInterface(this@RegistrationFragment, 1)
        cusPass.setMessage("Создайте пароль минимум из 8 символов, включая цифры, заглавные и строчные буквы")
        btnSendOtp.buttonAvailability(false)
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
        if(isNumberNotEmpty && isFirstPasswordNotEmpty && isSecondPasswordNotEmpty) {
            if(cusPass.getPasswordField() == cusPass1.getPasswordField()){
                btnSendOtp.buttonAvailability(true)
                textButton.visibility = View.VISIBLE
                textButton.movementMethod = LinkMovementMethod.getInstance()
                cusPass.setMessage("Создайте пароль минимум из 8 символов, включая цифры, заглавные и строчные буквы")
                cusPass1.setMessage("")
            } else {
                btnSendOtp.buttonAvailability(false)
                textButton.visibility = View.GONE
                cusPass1.setErrorMessage("Пароли не совпадают")
                cusPass.setErrorMessage("")
            }
        } else {
            btnSendOtp.buttonAvailability(false)
        }



        btnSendOtp.setOnClickListener{
            findNavController().navigate(R.id.registrationOtpFragment)

        }
    }
}