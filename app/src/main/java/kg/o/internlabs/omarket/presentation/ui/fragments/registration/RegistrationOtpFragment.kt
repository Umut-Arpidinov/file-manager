package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.OtpHelper
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegistrationOtpFragment :
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationViewModel>(), OtpHelper {

    private var args: RegistrationOtpFragmentArgs? = null

    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }
    private val prefs: StoragePreferences by lazy {
        StoragePreferences(requireContext())
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
            initObserver()
        }
    }


    private fun initObserver() {
        val reg = RegisterEntity(msisdn = args?.number, otp = binding.cusOtp.getValues())
        viewModel.checkOtp(reg)
        safeFlowGather {
            viewModel.checkOtp.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        prefs.userPhoneNumber = reg.msisdn
                        prefs.token = it.data.accessToken
                        prefs.refreshToken = it.data.refreshToken
                        findNavController().navigate(R.id.loginStartFragment)
                    }
                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(), "Неверный код", Toast.LENGTH_SHORT).show()
                    }
                    ApiState.Loading -> {

                    }
                }
            }
        }
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