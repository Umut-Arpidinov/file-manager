package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.OtpResend
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding

@AndroidEntryPoint
class RegistrationOtpFragment :
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationViewModel>(), OtpResend {
    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationOtpBinding {
        return FragmentRegistrationOtpBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()

        binding.cusOtp.setInterface(this)
        binding.btnSendOtp.buttonAvailability(false)
        //binding.cusOtp.setError("Niene")

        /* binding.otp.setInterface(this)

      binding.btnOtp.setOnClickListener {
          println( binding.otp.getValues())
          binding.otp.setError()
      }*/
//        val navHostFragment = requireActivity().supportFragmentManager
//            .findFragmentById(R.id.nav_host) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController.navigate(R.id.mainFragment)
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

    override fun watcher(empty: Boolean) {
        println("--jj------$empty")
        binding.btnSendOtp.buttonAvailability(empty)
    }

}