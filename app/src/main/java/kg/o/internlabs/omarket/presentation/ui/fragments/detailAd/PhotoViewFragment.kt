package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentPhotoViewBinding

class PhotoViewFragment : BaseFragment<FragmentPhotoViewBinding, DetailAdViewModel>() {
    override val viewModel: DetailAdViewModel by lazy {
        ViewModelProvider(this)[DetailAdViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentPhotoViewBinding {
        return FragmentPhotoViewBinding.inflate(layoutInflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        tbMedia.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun initViewModel() {
        super.initViewModel()
    }
}