package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.cells.CustomAddPriceCellView
import kg.o.internlabs.omarket.databinding.FragmentDetailedAdBinding
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.presentation.ui.fragments.ads.DetailedImageAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.adapter.CellAdapter
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
    val currentPrice = "10000.99"
    val currency = "dollar"
    val verified = true
    val oMoney = false
    val seller = "Murat"

    val imgURL2 =
        "https://play-lh.googleusercontent.com/p51P1MutZJY9410vLPCsF-IAUVBmPxt8hi4W-3PTFwZBSPJmraaGyMT5Uv49cRZYSw0"

    val arrayOfString: List<String> = listOf(imgURL2, imgURL2, imgURL2)

    val listOfDetails: MutableList<Pair<String, String>> =
        mutableListOf(
            Pair("Категория", "Электроника"),
            Pair("Подкатегория", "Телефоны"),
            Pair("Дата публикации", "11.11.22"),
            Pair("Тип сделки", "Продам"),
            Pair("Город", "Бишкек")
        )
    //--------------------------

    override val viewModel: DetailAdViewModel by lazy {
        ViewModelProvider(this)[DetailAdViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentDetailedAdBinding {
        return FragmentDetailedAdBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        title.text = getString(coreString.sample_title_of_the_product)
        setMainCardView(customMainView)

        adapter.setInterface(this@DetailAdFragment, this@DetailAdFragment)
        initAdapter()
        getAds()
    }

    override fun initListener() = with(binding) {
        super.initListener()
        tbAds.setOnClickListener {
            findNavController().navigateUp()
        }

        moreDetails.setOnClickListener {
            pressMoreDetails(moreDetails, description)
        }

        callBtn.setOnClickListener {
            callByNumber("+996555160301")
        }

        writeBtn.setOnClickListener {
            writeByPhone("+996555160301")
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
        initViewPagerAdapter(imageViewPager, currentPos)
        initCellAdapter(cellRecycler)
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

    private fun initViewPagerAdapter(imageViewPager: ViewPager2, currentPos: TextView) {
        val pagerAdapter = DetailedImageAdapter(
            requireContext(),
            arrayOfString,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageViewPager.adapter = pagerAdapter

        currentPos.text =
            String.format(getString(coreString.sample_indicator), 1, pagerAdapter.itemCount)

        imageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPos.text = String.format(
                    getString(coreString.sample_indicator),
                    position + 1,
                    pagerAdapter.itemCount
                )
            }
        })
    }

    private fun setMainCardView(customMainView: CustomAddPriceCellView) {
        if (currentPrice == null)
            customMainView.setPriceWithoutCoins(getString(coreString.null_price))
        else {
            if (currency == "som")
                customMainView.setPriceWithoutCoins(
                String.format(getString(coreString.som_price), currentPrice.toInt())
            )
            else {
                try {
                    customMainView.setPriceWithoutCoins(
                        String.format(getString(coreString.dollar_price_overview), currentPrice.toInt())
                    )
                } catch (e: NumberFormatException) {
                    var dol: String
                    var coin: String
                    if (currentPrice.contains(',')) {
                        dol = currentPrice.substringBefore(',')
                        coin = currentPrice.substringAfter(',')
                    } else {
                        dol = currentPrice.substringBefore('.')
                        coin = currentPrice.substringAfter('.')
                    }
                    customMainView.setPriceWithoutCoins(
                        String.format(getString(coreString.dollar_price_overview), dol.toInt())
                    )
                    customMainView.setPriceWithCoins(coin)
                }
            }
        }
        customMainView.isNumberVerified(verified)
        customMainView.isODengiAccepted(oMoney)
        customMainView.setTitle(seller)
    }

    private fun initCellAdapter(cellRecycler: RecyclerView) {
        cellRecycler.adapter = CellAdapter(listOfDetails)
        cellRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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