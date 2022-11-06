package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.CustomTextWatcher
import kg.o.internlabs.core.custom_views.OtpResend
import kg.o.internlabs.omarket.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(), OtpResend, CustomTextWatcher {

    private var isOtpNotEmpty = false
    private var isNumberNotEmpty = false
    private var isPasswordNotEmpty = false

    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater)
    }

    override fun initView() = with(binding){
        super.initView()

        cusOtp.setInterface(this@ProfileFragment)
        cusNum.setInterface(this@ProfileFragment, 1)
        cusPass.setInterface(this@ProfileFragment, 2)
        btnSendOtp.buttonAvailability(false)
    }

    override fun initListener() {
        super.initListener()
        binding.cusOtp.setOnClickListener {
        }

        binding.btnSendOtp.setOnClickListener {
            binding.cusOtp.setError("Niene")
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

    private fun complexWatcher() = with(binding){
        println("otp -----"+isOtpNotEmpty)
        println("num ------"+isNumberNotEmpty)
        println("pass ------"+isPasswordNotEmpty)
        println("--------------------------")

        btnSendOtp.buttonAvailability(isOtpNotEmpty && isNumberNotEmpty && isPasswordNotEmpty)
    }

    override fun watcher(empty: Boolean) {
       // println("--otp_watcher------$empty")
        isOtpNotEmpty = empty
        complexWatcher()
    }

    override fun numberWatcher(empty: Boolean, fieldsNumber: Int) {
        when(fieldsNumber) {
            1 -> isNumberNotEmpty = empty
            2 -> isPasswordNotEmpty = empty
        }
        complexWatcher()
    }

    override fun passwordWatcher(empty: Boolean, fieldsNumber: Int) {
        isPasswordNotEmpty = empty
        complexWatcher()
    }


}