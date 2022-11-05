package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentLoginEndBinding
import kg.o.internlabs.core.custom_views.OtpResend

@AndroidEntryPoint
class LoginEndFragment : BaseFragment<FragmentLoginEndBinding, LoginViewModel>(), OtpResend {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginEndBinding {
         return FragmentLoginEndBinding.inflate(inflater)
    }

    override fun initListener() {
        super.initListener()

        binding.otp.setInterface(this)

        binding.btnOtp.setOnClickListener {
            println( binding.otp.getValues())
            binding.otp.setError("Niene")
        }
//        val navHostFragment = requireActivity().supportFragmentManager
//            .findFragmentById(R.id.nav_host) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController.navigate(R.id.mainFragment)
    }

    override fun sendOtpAgain() {
        println()
        println("Would you not mind to send me, the otp one more time?")
        Toast.makeText(requireContext(), "Would you not mind to send me, the otp one more time?",
        Toast.LENGTH_LONG).show()
    }

    override fun watcher(empty: Boolean) {
        println("--jj------$empty")
    }

}