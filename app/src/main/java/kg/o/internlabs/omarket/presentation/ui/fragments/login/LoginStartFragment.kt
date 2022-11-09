package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.NumberInputHelper
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginStartBinding

@AndroidEntryPoint
class LoginStartFragment : BaseFragment<FragmentLoginStartBinding, LoginViewModel>(),
    NumberInputHelper {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginStartBinding {
        return FragmentLoginStartBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        cusBtnEnter.buttonAvailability(false)
        cusBtnReg.buttonAvailability(0)
    }

    override fun initListener() = with(binding) {
        super.initListener()

        // setting watcher
        cusNum.setInterface(this@LoginStartFragment)
        //findNavController().navigate(R.id.loginEndFragment)
        cusBtnReg.setOnClickListener {
            println("clicked reg")
            findNavController().navigate(R.id.registrationFragment)
        }
        cusBtnEnter.setOnClickListener {
            println("clicked enter")

                // val act = NavAction(R.id.loginEndFragment)
            //val directions = act.navOptions
            /*val amount = //amountTv.text.toString().toInt()

            val action = SpecifyAmountFragmentDirections.confirmationAction(amount)*/
            findNavController().navigate(R.id.loginEndFragment)
        }

    }


    override fun numberWatcher(notEmpty: Boolean, fieldsNumber: Int) {
        binding.cusBtnEnter.buttonAvailability(notEmpty)
    }


    // TODO чтобы получить значение номера телефона вызыаем геттер так binding.cusNum.getValues
}