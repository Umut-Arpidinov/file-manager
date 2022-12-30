package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.BottomSheetCategoriesBinding


class CategoriesBottomSheet : BaseFragment<BottomSheetCategoriesBinding, NewAdsViewModel>() {

    override val viewModel: NewAdsViewModel by lazy {
        ViewModelProvider(this)[NewAdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): BottomSheetCategoriesBinding {
        return BottomSheetCategoriesBinding.inflate(inflater)
    }

}