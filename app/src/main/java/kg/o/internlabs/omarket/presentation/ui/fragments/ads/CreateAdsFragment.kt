package kg.o.internlabs.omarket.presentation.ui.fragments.ads

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentCreateAdsBinding

class CreateAdsFragment : BaseFragment<FragmentCreateAdsBinding, CreateAdsViewModel>() {
    override val viewModel: CreateAdsViewModel by lazy {
        ViewModelProvider(this)[CreateAdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentCreateAdsBinding {
        return FragmentCreateAdsBinding.inflate(inflater)
    }
}