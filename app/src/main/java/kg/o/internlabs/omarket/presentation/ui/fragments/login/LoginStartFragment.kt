package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginStartBinding

@AndroidEntryPoint
class LoginStartFragment : BaseFragment<FragmentLoginStartBinding, LoginViewModel>(), NumberInputHelper {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginStartBinding {
         return FragmentLoginStartBinding.inflate(inflater)
    }

    override fun initListener() = with(binding) {
        super.initListener()

        // setting watcher
        cusNum.setInterface(this@LoginStartFragment)
        findNavController().navigate(R.id.loginEndFragment)
    }

    override fun initView() {
        super.initView()

    }

    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        // TODO Здесь можно управлять кнопкой если button.clickable = notEmpty
        // TODO если поле номера введен не полностью notEmpty = false
    }


    // TODO чтобы получить значение номера телефона вызыаем геттер так binding.cusNum.getValues
}