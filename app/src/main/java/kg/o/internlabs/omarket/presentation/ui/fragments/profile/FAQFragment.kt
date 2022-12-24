package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.view.LayoutInflater
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentFAQBinding
import kg.o.internlabs.omarket.presentation.ui.fragments.profile.adapter.FaqPagingAdapter
import kg.o.internlabs.omarket.utils.LoaderStateAdapter
import kg.o.internlabs.omarket.utils.loadListener
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FAQFragment : BaseFragment<FragmentFAQBinding, ProfileViewModel>() {

    private var adapter = FaqPagingAdapter()

    override val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentFAQBinding.inflate(inflater)

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getFaq()
    }

    override fun initView() {
        super.initView()
        initAdapter()
        visibleStatusBar()
        getFaqs()
    }

    override fun initListener() = with(binding) {
        super.initListener()
        tbFaq.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun initAdapter() = with(binding) {
        recyclerFaq.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )
        loadListener(adapter, progress, recyclerFaq)
    }

    private fun visibleStatusBar() {
        WindowInsetsControllerCompat(requireActivity().window,requireView()).show((WindowInsetsCompat.Type.statusBars()))
    }

    private fun getFaqs() {
        safeFlowGather {
            viewModel.faqs.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}