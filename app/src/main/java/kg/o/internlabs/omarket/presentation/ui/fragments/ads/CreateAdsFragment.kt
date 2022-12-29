package kg.o.internlabs.omarket.presentation.ui.fragments.ads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentCreateAdsBinding
import kg.o.internlabs.omarket.presentation.ui.fragments.main.PagerImageAdapter

class CreateAdsFragment : BaseFragment<FragmentCreateAdsBinding, CreateAdsViewModel>() {
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

    override val viewModel: CreateAdsViewModel by lazy {
        ViewModelProvider(this)[CreateAdsViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentCreateAdsBinding {
        return FragmentCreateAdsBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()

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
}