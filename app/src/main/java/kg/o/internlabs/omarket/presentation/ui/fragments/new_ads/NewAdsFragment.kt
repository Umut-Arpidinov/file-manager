package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentNewAdsBinding

class NewAdsFragment : BaseFragment<FragmentNewAdsBinding, NewAdsViewModel>() {
    override val viewModel: NewAdsViewModel by lazy {
        ViewModelProvider(this)[NewAdsViewModel::class.java]
    }
    override fun inflateViewBinding(inflater: LayoutInflater): FragmentNewAdsBinding {
        return FragmentNewAdsBinding.inflate(inflater)
    }
}