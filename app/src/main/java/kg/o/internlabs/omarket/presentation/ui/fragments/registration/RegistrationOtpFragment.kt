package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.OtpHelper
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response

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

    override fun initViewModel() {
        initObserver()
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

        val reg = RegisterEntity(msisdn = "996500997007", otp = "02305")
        viewModel.checkOtp(reg)

    }
    private fun safeFlowGather(action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                action()
            }
        }
    }

    fun initObserver() {
        safeFlowGather {
            viewModel.checkOtp.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        Log.d("Ray", "success 400")
                    }
                    is ApiState.Failure -> {

                        Log.d("Ray", it.msg.toString())
                    }
                    ApiState.Loading -> {
                        //TODO показать прогресс бар
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