package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.omarket.databinding.FragmentDetailedAdBinding
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.presentation.ui.fragments.ads.DetailedImageAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.adapter.SimilarAdsPagingAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.main.adapter.AdClickedInMain
import kg.o.internlabs.omarket.utils.LoaderStateAdapter
import kg.o.internlabs.omarket.utils.loadListener
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

private typealias coreString = kg.o.internlabs.core.R.string

@AndroidEntryPoint
class DetailAdFragment : BaseFragment<FragmentDetailedAdBinding, DetailAdViewModel>(),
    AdClickedInMain {

    private val args: DetailAdFragmentArgs by navArgs()
    private var adapter = SimilarAdsPagingAdapter()
    private var moreDetailIsPressed = false

    //Data for test-------------
    val imgURL2 =
        "https://play-lh.googleusercontent.com/p51P1MutZJY9410vLPCsF-IAUVBmPxt8hi4W-3PTFwZBSPJmraaGyMT5Uv49cRZYSw0"

    val arrayOfString: List<String> = listOf(imgURL2, imgURL2, imgURL2)
    //--------------------------

    override val viewModel: DetailAdViewModel by lazy {
        ViewModelProvider(this)[DetailAdViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentDetailedAdBinding {
        return FragmentDetailedAdBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        adapter.setInterface(this@DetailAdFragment, this@DetailAdFragment)
        initAdapter()
        getAds()
        args.uuid.let { title.text = it }

        val pagerAdapter =
            DetailedImageAdapter(
                requireContext(), arrayOfString, ViewGroup.LayoutParams.MATCH_PARENT)
        imageViewPager.adapter = pagerAdapter

        currentPos.text = String.format(getString(kg.o.internlabs.core.R.string.sample_indicator), 1, pagerAdapter.itemCount)
        imageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPos.text = String.format(getString(kg.o.internlabs.core.R.string.sample_indicator), position+1, pagerAdapter.itemCount)
            }
        })


        moreDetails.setOnClickListener {
            pressMoreDetails(moreDetails, description)
        }

        // Recycler for Ad details
//        cellRecycler.adapter = CellAdapter(list)
//        cellRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        callBtn.setOnClickListener {
            callByNumber("+996555160301")
        }

        writeBtn.setOnClickListener {
            writeByPhone("+996555160301")
        }
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

    override fun adClicked(ad: ResultX) {
        makeToast(args.uuid.toString())
    }

    private fun initAdapter() = with(binding) {
        recSimilarAds.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )
        loadListener(adapter, loadingAnim, recSimilarAds)
    }

    private fun getAds() {
        safeFlowGather {
            viewModel.ads?.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun pressMoreDetails(moreDetails: TextView, description: TextView) {
        moreDetailIsPressed = !moreDetailIsPressed
        if (moreDetailIsPressed) {
            description.maxLines = Int.MAX_VALUE
            description.ellipsize = null
            moreDetails.text = getString(coreString.less_details)
        } else {
            description.maxLines = 3
            description.ellipsize = TextUtils.TruncateAt.END
            moreDetails.text = getString(coreString.more_details)
        }
    }

    private fun callByNumber(number: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$number")
        startActivity(callIntent)
    }

    private fun writeByPhone(number: String) {
        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.data = Uri.parse("sms:$number")
        startActivity(smsIntent)
    }

    private fun writeByWA(number: String) {
        val intentWA = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$number"))
        intentWA.setPackage("com.whatsapp")
        startActivity(intentWA)
    }
}