package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.databinding.FragmentFAQBinding
import kg.o.internlabs.omarket.domain.entity.ResultsEntity
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FAQFragment : BaseFragment<FragmentFAQBinding, ProfileViewModel>() {

    private var list: List<ResultsEntity>? = listOf()

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
        getCategories()
    }

    override fun initListener() = with(binding) {
        super.initListener()
        tbFaq.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun getCategories() {
        safeFlowGather {
            viewModel.faqs.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        list = it.data.results
                        showFaq(list)
                    }
                    is ApiState.Failure -> {
                        requireActivity().makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                        // запрос обрабатывается сервером
                    }
                }
            }
        }
    }

    private fun showFaq(list: List<ResultsEntity>?) {
        binding.recyclerFaq.layoutManager = LinearLayoutManager(activity)
        binding.recyclerFaq.adapter = FaqAdapter(list!!)
    }
}