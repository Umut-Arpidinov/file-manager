package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd

import android.content.Intent
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kg.o.internlabs.core.base.BaseFragment
import kg.o.internlabs.core.custom_views.cells.CustomAddPriceCellView
import kg.o.internlabs.core.custom_views.cells.CustomRoundedOneCellLineView
import kg.o.internlabs.core.custom_views.cells.Position
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.BottomSheetOverviewBinding
import kg.o.internlabs.omarket.databinding.FragmentDetailedAdBinding
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.presentation.ui.fragments.ads.DetailedImageAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.adapter.CellAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.adapter.SimilarAdsPagingAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.main.MarginItemDecoration
import kg.o.internlabs.omarket.presentation.ui.fragments.main.adapter.AdClickedInMain
import kg.o.internlabs.omarket.utils.LoaderStateAdapter
import kg.o.internlabs.omarket.utils.loadListener
import kg.o.internlabs.omarket.utils.makeToast
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.adapter.ImageClickedAds

typealias coreString = kg.o.internlabs.core.R.string
private typealias coreDrawable = kg.o.internlabs.core.R.drawable

@AndroidEntryPoint
class DetailAdFragment : BaseFragment<FragmentDetailedAdBinding, DetailAdViewModel>(),
    AdClickedInMain, ImageClickedAds{
    private val WHATSAPP = "com.whatsapp"
    private val TELEGRAM = "org.telegram.messenger"

    private val args: DetailAdFragmentArgs by navArgs()
    private var adapter = SimilarAdsPagingAdapter()
    private var moreDetailIsPressed = false

    //Data for test-------------
    val currentPrice = "10000.99"
    val currency = "dollar"
    val verified = true
    val oMoney = false
    val seller = "Murat"
    val phone = "+996705532206"
    val nick = "areyouconfident"
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
            showDialog(phone, true, nick)
        }

        writeBtn.setOnClickListener {
            showDialog(phone, false, nick)
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getAds()
    }

    override fun adClicked(ad: ResultX) {
        makeToast(args.uuid.toString())
    }

    override fun imageClicked() {
        findNavController().navigate(DetailAdFragmentDirections.goToViewer())
    }

    private fun initAdapter() = with(binding) {
        initViewPagerAdapter(imageViewPager, currentPos)
        initCellAdapter(cellRecycler)
        recSimilarAds.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(
                R.dimen.item_margin_7dp))
        )
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
            ViewGroup.LayoutParams.MATCH_PARENT,
            false
        )
        imageViewPager.adapter = pagerAdapter
        pagerAdapter.setInterface(this@DetailAdFragment)

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
                        String.format(
                            getString(coreString.dollar_price_overview),
                            currentPrice.toInt()
                        )
                    )
                } catch (e: NumberFormatException) {
                    val dol: String
                    val coin: String
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

    private fun actionByNumber(number: String, isCall: Boolean) {
        if (isCall) {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$number")
            startActivity(callIntent)
        } else {
            val smsIntent = Intent(Intent.ACTION_VIEW)
            smsIntent.data = Uri.parse("sms:$number")
            startActivity(smsIntent)
        }
    }

    private fun whatsAppIntent(number: String) {
        try {
            val intentWA = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$number"))
            intentWA.setPackage("com.whatsapp")
            startActivity(intentWA)
        } catch (e: java.lang.Exception) {
            Toast.makeText(requireContext(), "WhatsApp is not Installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun telegramIntent(nickname: String?) {
        if (nickname != null) {
            try {
                val tgintent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://t.me/$nickname")
                )
                tgintent.setPackage(TELEGRAM)
                startActivity(tgintent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Telegram is not Installed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showDialog(number: String, isCall: Boolean, nickname: String?) {
        val binding = BottomSheetOverviewBinding.inflate(LayoutInflater.from(context))

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)

        with(binding) {
            icClose.setOnClickListener {
                dialog.dismiss()
            }

            if (isCall) appOption.setTitle("Звонок")
            else appOption.setTitle("SMS")

            if (!isAppInstalled(WHATSAPP))
                waOption.visibility = GONE
            else {
                //WHATSAPP icon is not correct
                //getAppIcon(waOption, WHATSAPP)

                //Delete after resolving
                waOption.hasIcon(true)
                waOption.setIcon(coreDrawable.ic_whatsapp)
                waOption.setOnClickListener {
                    whatsAppIntent(number)
                }
            }

            if (!isAppInstalled(TELEGRAM)) {
                tgOption.visibility = GONE
                waOption.setPosition(Position.BOTTOM)
            } else {
                getAppIcon(tgOption, TELEGRAM)
                tgOption.setOnClickListener {
                    telegramIntent(nickname)
                }
            }

            if (!isAppInstalled(WHATSAPP) && !isAppInstalled(TELEGRAM))
                appOption.setPosition(Position.SINGLE)

            appOption.setOnClickListener {
                actionByNumber(number, isCall)
            }
        }
        dialog.show()
    }

    @Suppress("DEPRECATION")
    private fun isAppInstalled(packageName: String): Boolean {
        return try {
            requireContext().packageManager.getApplicationInfo(packageName, 0).enabled
            true
        } catch (ignored: NameNotFoundException) {
            false
        }
    }

    private fun getAppIcon(waOption: CustomRoundedOneCellLineView, app: String) {
        try {
            val icon = requireContext().packageManager.getApplicationIcon(app)
            waOption.hasIcon(true)
            waOption.setIcon(icon)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }
    }
}