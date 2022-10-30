package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import kg.o.internlabs.core.BaseFragment
import kg.o.internlabs.core.custom_view.cells.CustomOneTitleTextCellsView
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentLoginFinalStateBinding
import kg.o.internlabs.omarket.presentation.viewmodels.fragmentsviewmodels.LoginViewModel

class LoginFragmentFinalState : BaseFragment<FragmentLoginFinalStateBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginFinalStateBinding {
         return FragmentLoginFinalStateBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        val a = "1234567890 Поле ввода комментария вмещает в себя такое же количество символов, сколько поле поле ввода комментария - 130 символов"
        println("----------- "+a.length)
    }

}