package kg.o.internlabs.omarket.presentation.ui.fragments.ads

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentAdsBinding

class AdsFragment : BaseFragment<FragmentAdsBinding, AdsViewModel>() {
    override val viewModel: AdsViewModel by lazy {
        ViewModelProvider(this)[AdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentAdsBinding {
        return FragmentAdsBinding.inflate(inflater)
    }
}