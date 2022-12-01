package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater)
    }
}