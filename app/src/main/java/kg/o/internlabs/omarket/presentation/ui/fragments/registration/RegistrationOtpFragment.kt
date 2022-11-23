package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.OtpHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        btnSendOtp.isEnabled = false
    }

    override fun initListener() = with(binding){
        super.initListener()
        tbRegistrationOtp.setNavigationOnClickListener { findNavController().navigateUp() }
        btnSendOtp.setOnClickListener {
            viewModel.checkOtp(RegisterEntity(msisdn = args?.number?.let { it1 ->
                viewModel.formattedValues(it1)
            }, otp = cusOtp.getValues()))
            initObserver()
        }
    }

    private fun safeFlowGather(action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                action()
            }
        }
    }

    private fun initObserver() = with(binding){
        safeFlowGather {
            viewModel.checkOtp.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        if (args != null){
                            if (args!!.number != null) {
                                viewModel.putNumber(viewModel.formattedValues(args!!.number!!))
                            }
                            if (args!!.password != null) {
                                viewModel.putPwd(args!!.password!!)
                            }
                        }
                        try {
                            findNavController().navigate(R.id.mainFragment)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    is ApiState.Failure -> {
                        btnSendOtp.isVisible = true
                        btnSendOtp.isEnabled = false
                        progressBar.isVisible = false
                        it.msg.message?.let { it1 -> cusOtp.setError(it1) }
                    }
                    ApiState.Loading -> {
                        btnSendOtp.isVisible = false
                        progressBar.isVisible = true
                    }
                    else -> {}
                }
            }
        }
    }

    override fun sendOtpAgain() {
        viewModel.checkOtp(RegisterEntity(msisdn = args?.number?.let { it1 ->
            viewModel.formattedValues(it1)
        }, otp = binding.cusOtp.getValues()))
        initObserver()
    }

    override fun watcher(notEmpty: Boolean) {
        binding.btnSendOtp.isEnabled = notEmpty
    }
}
