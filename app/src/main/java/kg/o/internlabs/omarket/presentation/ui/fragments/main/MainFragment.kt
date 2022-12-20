package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.FragmentMainBinding
import kg.o.internlabs.omarket.domain.entity.ResultEntity
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>(),CategoryClickHandler{

    private var args: MainFragmentArgs? = null
    private var list: List<ResultEntity>? = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = MainFragmentArgs.fromBundle(requireArguments())

    }

    override val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentMainBinding.inflate(inflater)

    override fun initListener() = with(binding) {
        super.initListener()
        icProfile.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.goToProfile(args?.number))
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getAccessTokenFromPrefs()
        viewModel.getCategories()
        viewModel.getAds(1)
    }

    override fun initView() {
        super.initView()
        getCategories()
        getAds()
        visibleStatusBar()
    }

    private fun visibleStatusBar() {
        WindowInsetsControllerCompat(requireActivity().window,requireView()).show((WindowInsetsCompat.Type.statusBars()))
    }

    private fun initRecyclerViewAdapter(list: List<ResultEntity>?) {
        binding.categoryRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecycler.adapter = CategoryRecyclerViewAdapter(list, requireContext(),this)
    }

    private fun getCategories() {
        this@MainFragment.safeFlowGather {
            viewModel.categories.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        //TODO it.date.result вернет List<ResultEntity> в нем хранятся
                        //TODO it.data.result?.get(0)?.name  categoryName
                        //TODO it.data.result?.get(0)?.iconImg  icon
                        //TODO it.data.result   это для категорий все
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
                        requireActivity().makeToast(it.msg.message.toString())
                    }
                    is ApiState.Loading -> {
                    }
                }
            }
        }
    }

    private fun getAds() {
        this@MainFragment.safeFlowGather {
            viewModel.ads.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        binding.loadingAnim.visibility = GONE
                        val mainAdapter =
                            AdsListAdapter(
                                it.data?.result?.results!!,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                requireContext()
                            )
                        binding.mainViewHolder.addItemDecoration(
                            MarginItemDecoration(
                                resources.getDimensionPixelSize(
                                    R.dimen.item_margin_7dp
                                )
                            )
                        )
                        binding.mainViewHolder.adapter = mainAdapter
                    }
                    is ApiState.Failure -> {

                    }
                    is ApiState.Loading -> {
                        binding.loadingAnim.visibility = VISIBLE
                    }
                }
            }
        }

    }

    override fun clickedCategory(item: ResultEntity) {
        Toast.makeText(requireActivity(), "${item.name} with id ${item.id} was clicked", Toast.LENGTH_SHORT).show()
    }
}