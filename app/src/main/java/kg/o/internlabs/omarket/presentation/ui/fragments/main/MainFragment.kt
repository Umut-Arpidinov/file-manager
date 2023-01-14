package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentMainBinding
import kg.o.internlabs.omarket.domain.entity.ResultEntity
import kg.o.internlabs.omarket.domain.entity.ads.*
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
        initSearchView()
        getAds()
        visibleStatusBar()
        loadStateListener()
    }

    override fun initListener() = with(binding) {
        super.initListener()
        icProfile.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.goToProfile(args?.number))
        }
    }

    private fun visibleStatusBar() {
        WindowInsetsControllerCompat(requireActivity().window, requireView())
            .show((WindowInsetsCompat.Type.statusBars()))
    }

    private fun initAdapter() = with(binding) {
        recMain.addItemDecoration(
            MarginItemDecoration(2, resources.getDimensionPixelSize(R.dimen.item_margin_7dp), true))
        recMain.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )
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
    private fun getAdsByFilter(search: String?) {
        viewModel.getAdsByFilter(
            AdsByFilter(
                mainFilters = MainFilters(
                    q = search
                )
            )
        )
        getAds()
    }
    private fun loadStateListener() = with(binding) {
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                loadingAnim.isVisible = true
                cardViewErrorSearch.isVisible = false
            }
            if (loadState.refresh !is LoadState.Loading) {
                if (loadState.refresh is LoadState.Error || adapter.itemCount() < 1) {
                    loadingAnim.isVisible = false
                    recMain.isVisible = false
                    cardViewErrorSearch.isVisible = true
                } else {
                    recMain.isVisible = true
                    cardViewErrorSearch.isVisible = false
                    loadingAnim.isVisible = false
                }
            }
        }
    }

    private fun initSearchView() = with(binding) {
        searchMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (searchMain.query.trim().isNotEmpty() && query!!.length > 2) {
                    getAdsByFilter(query)
                    searchMain.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (searchMain.query.trim().isNotEmpty() && newText!!.length > 2) {
                    getAdsByFilter(newText)
                }
                if (searchMain.query.trim().isEmpty()) {
                    viewModel.getAds(
                        AdsByCategory(
                            mainFilter = MainFilter(
                                orderBy = "new",
                            )
                        )
                    )
                }
                getAds()
                return true
            }

        })
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
                    categoryId = item,
                    q = binding.searchMain.query.toString()
                )
            )
        )
        getAds()
    }

    override fun adClicked(ad: ResultX) {
        findNavController().navigate(MainFragmentDirections.goToAds("fork" + ad.uuid.toString()))
    }
}