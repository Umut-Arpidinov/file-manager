package kg.o.internlabs.omarket.presentation.ui.fragments.login

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentPdfBinding

@AndroidEntryPoint
class PdfFragment : BaseFragment<FragmentPdfBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentPdfBinding {
        return FragmentPdfBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        binding.pdfView.fromAsset("Market.pdf").load()
    }

}