package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.Register
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationOtpFragment :
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationOtpViewModel>() {
    private val prefs: StoragePreferences by lazy {
        StoragePreferences(requireContext())
    }

    override val viewModel: RegistrationOtpViewModel by lazy {
        ViewModelProvider(this)[RegistrationOtpViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationOtpBinding {
        return FragmentRegistrationOtpBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
    }

    override fun initViewModel() {
        initObserver()
    }

    override fun initListener() {
        val reg = Register(msisdn = "996500997007", otp = "7197")
        viewModel.checkOtp(reg)
    }

    fun safeFlowGather(action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                action()
            }
        }
    }

    fun initObserver() {
        safeFlowGather {
            viewModel.movieState.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        prefs.otp = it.data.accessToken
                        Log.d("Ray", prefs.otp.toString())

                        Log.d("Ray", it.data.toString())
                    }
                    is ApiState.Failure -> {
                        Log.d("Ray", it.msg.toString())

                    }
                    ApiState.Loading -> {
                        Log.d("Ray", "Loading epta")
                    }
                }
            }
        }
    }
}