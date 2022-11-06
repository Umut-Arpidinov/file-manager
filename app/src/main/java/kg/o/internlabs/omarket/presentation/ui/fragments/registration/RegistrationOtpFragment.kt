package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.OtpHelper
import kg.o.internlabs.omarket.databinding.FragmentRegistrationOtpBinding

@AndroidEntryPoint
class RegistrationOtpFragment :
    BaseFragment<FragmentRegistrationOtpBinding, RegistrationViewModel>(), OtpHelper {
    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationOtpBinding {
        return FragmentRegistrationOtpBinding.inflate(inflater)
    }

    override fun initListener() {
        super.initListener()
        //подключаем листенер
        //cusOtp.setInterface(this@RegistrationOtpFragment)

    }

    override fun sendOtpAgain() {
        //TODO если смс не пришла то можно обратно отсяда запросить код заново
    }

    override fun watcher(notEmpty: Boolean) {
        //TODO смотрить за полями для ввода отп кода
    }
}