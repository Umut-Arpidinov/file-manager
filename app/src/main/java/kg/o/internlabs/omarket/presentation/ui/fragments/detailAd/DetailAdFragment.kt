package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd

import android.content.Intent
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
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
import kg.o.internlabs.core.common.ApiState
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
    AdClickedInMain, ImageClickedAds {
    private val _whatsapp = "com.whatsapp"
    private val _telegram = "org.telegram.messenger"

    private val args: DetailAdFragmentArgs? by navArgs()
    private var adapter = SimilarAdsPagingAdapter()
    private var moreDetailIsPressed = false
    private var isMine = false

    override val viewModel: DetailAdViewModel by lazy {
        ViewModelProvider(this)[DetailAdViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentDetailedAdBinding {
        return FragmentDetailedAdBinding.inflate(inflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        isMine = args?.uuid?.substring(0, 4).toBoolean()
        getDetailAd()
    }

    override fun initListener() = with(binding) {
        super.initListener()
        tbAds.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        args?.uuid?.substring(4)?.let { it -> viewModel.getDetailAd(it) }
    }

    override fun adClicked(ad: ResultX) {
        makeToast(ad.uuid.toString())
    }

    override fun imageClicked() {
        findNavController().navigate(DetailAdFragmentDirections.goToViewer())
    }

    private fun getDetailAd() {
        safeFlowGather {
            viewModel.detailAd.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        it.data.resultX.let { it1 -> setDataToViews(it1, isMine) }
                        println("++++++++" + it.data.resultX)
                    }
                    is ApiState.Failure -> {
                        println("--....1.." + it.msg.message)
                    }
                    is ApiState.Loading -> {
                    }
                }
            }
        }
    }

    private fun setDataToViews(ad: ResultX?, isMine: Boolean) = with(binding) {
        if (isMine) {
            callBtn.text = "Редактировать"
            writeBtn.visibility = GONE
            textBeforeAds.visibility = GONE
            customMainView.isOwnAd()
        }
        if (!isMine) {
            adapter.setInterface(this@DetailAdFragment, this@DetailAdFragment)
            initAdapter()
            getAds()
        }

        initViewPagerAdapter(imageViewPager, currentPos, ad?.minifyImages)
        initCellAdapter(cellRecycler, ad)

        if (ad?.promotionType?.type != "VIP")
            iconVipUser.visibility = GONE

        if (ad?.author?.verified == null || !ad.author.verified)
            iconCheckedUser.visibility = GONE

        title.text = ad?.title
        description.text = ad?.description

        if (description.lineCount <= 3) {
            moreDetails.visibility = GONE
        } else {
            moreDetails.setOnClickListener {
                pressMoreDetails(moreDetails, description)
            }
        }

        setMainCardView(customMainView, ad)

        callBtn.setOnClickListener {
            if (isMine) {
                findNavController().navigate(DetailAdFragmentDirections.goToEditFragment())
            } else {
                showDialog(ad?.author?.contactNumber!!, true, ad.telegramProfile)
            }
        }
        writeBtn.setOnClickListener {
            showDialog(ad?.author?.contactNumber!!, false, ad.telegramProfile)
        }
    }

    override fun onStart() {
        super.onStart()
        val timer = object: CountDownTimer(1200, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                binding.progressInAction.visibility = GONE
                binding.parentScroll.visibility = VISIBLE}
        }
        timer.start()
    }

    private fun initAdapter() = with(binding) {
        recSimilarAds.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.item_margin_7dp
                )
            )
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

    private fun initViewPagerAdapter(
        imageViewPager: ViewPager2,
        currentPos: TextView,
        minifyImages: List<String>?
    ) {
        val pagerAdapter = DetailedImageAdapter(
            requireContext(),
            minifyImages,
            ViewGroup.LayoutParams.MATCH_PARENT,
            false,
            activity
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

    private fun setMainCardView(customMainView: CustomAddPriceCellView, ad: ResultX?) {
        if (ad?.price == null)
            customMainView.setPriceWithoutCoins(getString(coreString.null_price))
        else {
            val price = ad.price.toInt().formatDecimalSeparator()
            if (ad.currency == "som")
                customMainView.setPriceWithoutCoins(
                    price, true
                )
            else {
                try {
                    customMainView.setPriceWithoutCoins(
                        String.format(
                            getString(coreString.dollar_price_overview),
                            price
                        )
                    )
                } catch (e: NumberFormatException) {
                    val dol: String
                    val coin: String
                    if (ad.price.contains(',')) {
                        dol = ad.price.substringBefore(',')
                        coin = ad.price.substringAfter(',')
                    } else {
                        dol = ad.price.substringBefore('.')
                        coin = ad.price.substringAfter('.')
                    }
                    customMainView.setPriceWithoutCoins(
                        String.format(getString(coreString.dollar_price_overview), dol.toInt())
                    )
                    customMainView.setPriceWithCoins(coin)
                }
            }
        }
        customMainView.isNumberVerified(ad?.author?.verified!!)
        customMainView.isODengiAccepted(ad.oMoneyPay!!)
        customMainView.setTitle(ad.author.username!!)
    }

    private fun initCellAdapter(cellRecycler: RecyclerView, ad: ResultX?) {
        val listOfDetails: MutableList<Pair<String, String>> = mutableListOf()

        if (ad?.category?.name != null && ad.category.name != "")
            listOfDetails.add(Pair("Категория", ad.category.name))

        if (ad?.category?.categoryType != null && ad.category.categoryType != "")
            listOfDetails.add(Pair("Подкатегория", ad.category.categoryType))

        if (ad?.createdAt != null && ad.createdAt != "") {
            var date = ad.createdAt.substring(0, 10)
            date = getString(
                R.string.date,
                date.substring(8, 10),
                date.substring(5, 7),
                date.substring(0, 4)
            )
            listOfDetails.add(Pair("Дата публикации", date))
        }

        if (ad?.location?.name != null && ad.location.name != "")
            listOfDetails.add(Pair("Город", ad.location.name!!))


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
                val tgIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://t.me/$nickname")
                )
                tgIntent.setPackage(_telegram)
                startActivity(tgIntent)
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

            if (!isAppInstalled(_whatsapp))
                waOption.visibility = GONE
            else {
                waOption.hasIcon(true)
                waOption.setIcon(coreDrawable.ic_whatsapp)
                waOption.setOnClickListener {
                    whatsAppIntent(number)
                }
            }

            if (!isAppInstalled(_telegram)) {
                tgOption.visibility = GONE
                waOption.setPosition(Position.BOTTOM)
            } else {
                getAppIcon(tgOption)
                tgOption.setOnClickListener {
                    telegramIntent(nickname)
                }
            }

            if (!isAppInstalled(_whatsapp) && !isAppInstalled(_telegram))
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

    private fun getAppIcon(waOption: CustomRoundedOneCellLineView) {
        try {
            val icon = requireContext().packageManager.getApplicationIcon(_telegram)
            waOption.hasIcon(true)
            waOption.setIcon(icon)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun Int.formatDecimalSeparator(): String {
        return toString()
            .reversed()
            .chunked(3)
            .joinToString(" ")
            .reversed()
    }
}