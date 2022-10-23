package kg.o.internlabs.omarket.presentation.ui.fragments

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentProfileBinding
import kg.o.internlabs.omarket.presentation.viewmodels.fragmentsviewmodels.ProfileViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater)
    }


}