package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.CustomTextWatcher
import kg.o.internlabs.core.custom_views.OtpResend
import kg.o.internlabs.omarket.R
import kg.o.internlabs.core.custom_views.OtpHelper
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding

@AndroidEntryPoint
class RegistrationOtpFragment :
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationViewModel>(), OtpHelper {
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationViewModel>(), OtpResend {

    private var isOtpNotEmpty = false
    private var isNumberNotEmpty = false
    private var isPasswordNotEmpty = false


    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationOtpBinding {
        return FragmentRegistrationOtpBinding.inflate(inflater)
    }

    override fun initView() = with(binding){
        super.initView()

        //cusOtp.setInterface(this@RegistrationOtpFragment)
        btnSendOtp.buttonAvailability(true)
        btnSendOtp1.buttonAvailability(false)
        btnSendOtp2.buttonActivated()
    }

    override fun initListener() {
        super.initListener()
        binding.btnSendOtp.setOnClickListener {
            Toast.makeText(
                requireContext(), "clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnSendOtp1.setOnClickListener {
            Toast.makeText(
                requireContext(), "clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnSendOtp2.setOnClickListener {
            Toast.makeText(
                requireContext(), "clicked",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun sendOtpAgain() {
        println()
        println("Would you not mind to send me, the otp one more time?")
        binding.btnSendOtp.buttonActivated()
        Toast.makeText(
            requireContext(), "Would you not mind to send me, the otp one more time?",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun complexWatcher() {
        binding.btnSendOtp.buttonAvailability(isOtpNotEmpty && isNumberNotEmpty && isPasswordNotEmpty)
    }

    override fun watcher(empty: Boolean) {
        println("--otp_watcher------$empty")
        complexWatcher()
    }

}