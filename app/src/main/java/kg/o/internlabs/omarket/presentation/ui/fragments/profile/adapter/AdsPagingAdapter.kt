package kg.o.internlabs.omarket.presentation.ui.fragments.profile.adapter

import android.graphics.Paint
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.databinding.CardViewUsersAdsBinding
import kg.o.internlabs.omarket.domain.entity.MyAdsResultsEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.coreString
import kg.o.internlabs.omarket.presentation.ui.fragments.main.PagerImageAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.profile.ProfileFragment
import kg.o.internlabs.omarket.utils.BasePagingAdapter
import kg.o.internlabs.omarket.utils.makeToast

class AdsPagingAdapter: PagingDataAdapter<MyAdsResultsEntity, AdsPagingAdapter.AdsViewHolder>
    (AdsComparator), BasePagingAdapter {
    private lateinit var adClicked: AdClicked
    private var fragmentContext: ProfileFragment? = null

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> adClicked.adClicked(it1) }
        }
    }

    fun setInterface(adClicked: AdClicked, profileFragment: ProfileFragment) {
        this.adClicked = adClicked
        fragmentContext = profileFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardViewUsersAdsBinding.inflate(inflater, parent, false)
        return AdsViewHolder(binding)
    }

    inner class AdsViewHolder(val binding: CardViewUsersAdsBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyAdsResultsEntity?) = with(binding) {
            imgAds.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            imgAds.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    imgAds.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    imgAds.layoutParams.height = imgAds.width //height is ready
                }
            })

            vipIcon.isVisible = item?.promotionType?.type == "vip"
            oPayIcon.isVisible = item?.oMoneyPay ?: false
            setPager(item?.minifyImages, binding)
            setPrice(item?.price, item?.currency)
            oldPriceProduct.isVisible = item?.oldPrice.isNullOrEmpty().not()
            if (oldPriceProduct.isVisible) {
                item?.let { setOldPriceWithCurrency(it) }
            }
            nameProduct.text = item?.title
            nameCategory.text = item?.category?.name
            placeProduct.text = item?.let { placeAndDelivery(it) }
            viewCount.text = item?.viewCount

            adsBtn.setOnClickListener {
                fragmentContext?.requireActivity()?.makeToast("Sell faster clicked")
            }
        }

        private fun setPager(minifyImages: List<String>?, binding: CardViewUsersAdsBinding) =
            with(binding) {
                val pagerAdapter2 =
                    fragmentContext?.let {
                        PagerImageAdapter(
                            it.requireActivity(),
                            minifyImages,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                imgAds.adapter = pagerAdapter2
            }

        private fun setPrice(price: String?, currency: String?): Unit = with(binding) {
            if (price == null || price == "") priceProduct.text = getString(coreString.null_price)
            else {
                if (currency == "som") {
                    priceProduct.text = setSomPrice(price)
                } else if (currency != null) {
                    priceProduct.text = String.format(
                        getString(coreString.dollar_price),
                        price.toInt().formatDecimalSeparator()
                    )
                } else {
                    priceProduct.text = price.toInt().formatDecimalSeparator()
                }
            }
        }

        private fun setOldPriceWithCurrency(item: MyAdsResultsEntity) = with(binding) {
            if (item.oldPrice == null || item.oldPrice == "") {
                oldPriceProduct.visibility = View.GONE
            } else {
                oldPriceProduct.visibility = View.VISIBLE
                if (item.oldPrice == "10000 с") oldPriceProduct.text = item.oldPrice
                else oldPriceProduct.text = item.oldPrice.toInt().formatDecimalSeparator()
                oldPriceProduct.paintFlags =
                    oldPriceProduct.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }

    private fun placeAndDelivery(item: MyAdsResultsEntity?) =
        if (item?.category?.delivery == true) {
            fragmentContext?.requireActivity()
                ?.getString(kg.o.internlabs.core.R.string.delivery_available)
                ?.let { String.format(it, item.location?.name) }
        } else {
            item?.location?.name
        }

    object AdsComparator : DiffUtil.ItemCallback<MyAdsResultsEntity>() {
        override fun areItemsTheSame(
            oldItem: MyAdsResultsEntity,
            newItem: MyAdsResultsEntity
        ): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(
            oldItem: MyAdsResultsEntity,
            newItem: MyAdsResultsEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    private fun setSomPrice(price: String?): SpannableString {
        val resultString = String.format(getString(coreString.som_underline), price?.toInt()?.formatDecimalSeparator())
        val spannableString = SpannableString(resultString)
        spannableString.setSpan(
            UnderlineSpan(), resultString.lastIndex,
            resultString.length, 0
        )
        return spannableString
    }

    private fun getString(@StringRes resId: Int): String {
        return fragmentContext?.resources?.getString(resId) ?: ""
    }

    private fun Int.formatDecimalSeparator(): String {
        return toString()
            .reversed()
            .chunked(3)
            .joinToString(" ")
            .reversed()
    }
}