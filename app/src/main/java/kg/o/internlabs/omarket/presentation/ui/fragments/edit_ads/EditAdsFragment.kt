package kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentEditAdsBinding

class EditAdsFragment : BaseFragment<FragmentEditAdsBinding, EditAdsViewModel>() {
    override val viewModel: EditAdsViewModel by lazy {
        ViewModelProvider(this)[EditAdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentEditAdsBinding {
        return FragmentEditAdsBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
       // binding.text.text = requireActivity().getString(R.string.hello)
    }
}