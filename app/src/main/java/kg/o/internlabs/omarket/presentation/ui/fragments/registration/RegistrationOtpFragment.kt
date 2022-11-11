package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.core.custom_views.OtpHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.core.data.local.prefs.StoragePreferences
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response

private fun <T> getError(response: Response<T>): String {
    val gson = Gson()
    val type = object : TypeToken<RegisterDto>() {}.type
    val errorResponse: RegisterDto? = gson.fromJson(response.errorBody()?.charStream(), type)
    return errorResponse?.message.toString()
}

@AndroidEntryPoint
class RegistrationOtpFragment :
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationViewModel>(), OtpHelper {

    private var args: RegistrationOtpFragmentArgs? = null

    private val prefs: StoragePreferences by lazy {
        StoragePreferences(requireContext())
    }

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

    override fun initViewModel() {
        initObserver()
    }

    override fun initListener() {
        val reg = RegisterEntity(msisdn = "996500997007", otp = "02305")
        viewModel.checkOtp(reg)
        super.initListener()
        binding.btnSendOtp.setOnClickListener {
            findNavController().navigate(R.id.loginStartFragment)
        }
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
        //TODO если смс не пришла то можно обратно отсюда запросить код заново
    }

    override fun watcher(notEmpty: Boolean) {
        binding.btnSendOtp.buttonAvailability(notEmpty)
    }
}




