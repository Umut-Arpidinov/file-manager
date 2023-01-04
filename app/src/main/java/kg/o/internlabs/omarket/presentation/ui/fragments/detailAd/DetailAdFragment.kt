package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentCreateAdsBinding
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.adapter.SimilarAdsPagingAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.main.PagerImageAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.main.adapter.AdClickedInMain
import kg.o.internlabs.omarket.utils.LoaderStateAdapter
import kg.o.internlabs.omarket.utils.loadListener
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailAdFragment : BaseFragment<FragmentCreateAdsBinding, DetailAdViewModel>(),AdClickedInMain{

    private val args: DetailAdFragmentArgs by navArgs()
    private var adapter = SimilarAdsPagingAdapter()

    //Data for test-------------
    val imgURL2 = "https://play-lh.googleusercontent.com/p51P1MutZJY9410vLPCsF-IAUVBmPxt8hi4W-3PTFwZBSPJmraaGyMT5Uv49cRZYSw0"

    val arrayOfString: List<String> = listOf(imgURL2, imgURL2, imgURL2)
    val list: MutableList<Pair<String, String>> =
        mutableListOf(
            Pair("Category", "Cars"),
            Pair("Under", "BMW"),
            Pair("Date of", "11.11.22"),
            Pair("Type of sale", "Buy"),
            Pair("", ""))
    //--------------------------

    override val viewModel: DetailAdViewModel by lazy {
        ViewModelProvider(this)[DetailAdViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentCreateAdsBinding {
        return FragmentCreateAdsBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        adapter.setInterface(this@DetailAdFragment,this@DetailAdFragment)
        initAdapter()
        getAds()
        args.uuid.let { title.text = it }

        val pagerAdapter =
            PagerImageAdapter(requireContext(), arrayOfString, ViewGroup.LayoutParams.MATCH_PARENT)
            imageViewPager.adapter = pagerAdapter

        for (i in list.indices) {
            if (list[i].second == "" || list[i].second.isEmpty())
                list.removeAt(i)
        }

        cellRecycler.adapter = CellAdapter(list)
        cellRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    override fun initListener() {
        super.initListener()
        binding.tbAds.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getAds()
    }

    private fun initAdapter() = with(binding) {
        recSimilarAds.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )
        loadListener(adapter,loadingAnim,recSimilarAds)
    }

    private fun getAds() {
        safeFlowGather {
            viewModel.ads?.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun adClicked(ad: ResultX) {
        makeToast(args.uuid.toString())
    }

}