package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentRegistrationEmptyFieldsBinding
import kg.o.internlabs.omarket.presentation.viewmodels.fragmentsviewmodels.RegistrationViewModel

class RegistrationFragmentEmptyFields : BaseFragment<FragmentRegistrationEmptyFieldsBinding,
        RegistrationViewModel>() {
    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentRegistrationEmptyFieldsBinding {
        return FragmentRegistrationEmptyFieldsBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
    }

    override fun initViewModel() {
        super.initViewModel()
    }

    override fun initListener() {
        super.initListener()
       /* val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        //navController.navigate(R.id.)*/
    }
}