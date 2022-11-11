package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.OtpHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding

@AndroidEntryPoint
class RegistrationOtpFragment :
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationViewModel>(), OtpHelper {

    private var args: RegistrationOtpFragmentArgs? = null

    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationOtpBinding {
        return FragmentRegistrationOtpBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = RegistrationOtpFragmentArgs.fromBundle(requireArguments())
    }

    override fun initView() = with(binding) {
        super.initView()

        tvInfo.text = requireActivity().getString(R.string.info, args?.number)
        cusOtp.setInterface(this@RegistrationOtpFragment)
        btnSendOtp.buttonAvailability(false)
    }

    override fun initListener() {
        super.initListener()
        binding.btnSendOtp.setOnClickListener {
            findNavController().navigate(R.id.loginStartFragment)
        }

        binding.tbRegistrationOtp.setNavigationOnClickListener { findNavController().navigateUp() }

    }

    override fun sendOtpAgain() {
        Toast.makeText(
            requireContext(), "Would you not mind to send me, the otp one more time?",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun watcher(notEmpty: Boolean) {
        binding.btnSendOtp.buttonAvailability(notEmpty)
    }
}