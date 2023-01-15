package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentMainBinding
import kg.o.internlabs.omarket.domain.entity.ResultEntity
import kg.o.internlabs.omarket.domain.entity.ads.AdsByCategory
import kg.o.internlabs.omarket.domain.entity.ads.MainFilter
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.presentation.ui.fragments.main.adapter.AdClickedInMain
import kg.o.internlabs.omarket.presentation.ui.fragments.main.adapter.PagingAdapterForMain
import kg.o.internlabs.omarket.utils.LoaderStateAdapter
import kg.o.internlabs.omarket.utils.loadListener
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>(),
    CategoryClickHandler, AdClickedInMain {

    private var args: MainFragmentArgs? = null
    private var adapter = PagingAdapterForMain()

    private var list: List<ResultEntity>? = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = MainFragmentArgs.fromBundle(requireArguments())
    }

    override fun onStart() {
        super.onStart()
        adapter.addOnPagesUpdatedListener {
            binding.loadingAnim.visibility = View.GONE
            binding.cl.visibility = VISIBLE
            binding.floatingButton.visibility = VISIBLE
        }
    }

    override val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentMainBinding.inflate(inflater)

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getCategories()
    }

    override fun initView() {
        super.initView()
        adapter.setInterface(this@MainFragment, this)
        initAdapter()
        getCategories()
        getAds()
    }

    override fun initListener() = with(binding) {
        super.initListener()
        icProfile.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.goToProfile(args?.number))
        }
    }

    private fun initAdapter() = with(binding) {
        recMain.addItemDecoration(
            MarginItemDecoration(2, resources.getDimensionPixelSize(R.dimen.item_margin_7dp), true))
        recMain.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )
        loadListener(adapter, loadingAnim, recMain)
    }

    private fun initRecyclerViewAdapter(list: List<ResultEntity>?) {
        binding.categoryRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecycler.adapter =
            CategoryRecyclerViewAdapter(list, requireContext(), this)
    }

    private fun getCategories() {
        safeFlowGather {
            viewModel.categories.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        list = it.data.result
                        val arr = list?.toMutableList()
                        arr?.add(
                            0,
                            ResultEntity(
                                iconImg = kg.o.internlabs.core.R.drawable.category_all_union.toString(),
                                name = "Все"
                            )
                        )
                        initRecyclerViewAdapter(arr)
                    }
                    is ApiState.Failure -> {
                        makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                    }
                }
            }
        }
    }

    private fun getAds() {
        safeFlowGather {
            viewModel.ads?.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun clickedCategory(item: Int?) {
        if (item == null) {
            viewModel.getAds()
            getAds()
            return
        }
        viewModel.getAds(
            AdsByCategory(
                mainFilter = MainFilter(
                    orderBy = "new",
                    categoryId = item
                )
            )
        )
        makeToast("$item with id was clicked")
        getAds()
    }

    override fun adClicked(ad: ResultX) {
        findNavController().navigate(MainFragmentDirections.goToAds("fork" + ad.uuid.toString()))
    }
}