package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.adapter

import android.graphics.Paint
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.databinding.CardViewMainAdsBinding
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.DetailAdFragment
import kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.coreString
import kg.o.internlabs.omarket.presentation.ui.fragments.main.PagerImageAdapter
import kg.o.internlabs.omarket.presentation.ui.fragments.main.adapter.AdClickedInMain
import kg.o.internlabs.omarket.utils.BasePagingAdapter


class SimilarAdsPagingAdapter : PagingDataAdapter<ResultX, SimilarAdsPagingAdapter.SimilarAdsHolder>
    (SimilarAdsComparator), BasePagingAdapter {
    private var favorite = true
    private lateinit var adClicked: AdClickedInMain
    private var fragmentContext: DetailAdFragment? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SimilarAdsHolder(CardViewMainAdsBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: SimilarAdsHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> adClicked.adClicked(it1) }

        }
    }

    fun setInterface(adClicked: AdClickedInMain, createAdsFragment: DetailAdFragment) {
        this.adClicked = adClicked
        fragmentContext = createAdsFragment
    }

    inner class SimilarAdsHolder(private val binding: CardViewMainAdsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultX?) = with(binding) {
            imgAds.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT

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

            favoriteIcon.setOnClickListener {
                if (!favorite) {
                    favoriteIcon.setImageResource(kg.o.internlabs.core.R.drawable.ic_favorite_pressed)
                    favorite = true
                } else {
                    favorite = false
                    favoriteIcon.setImageResource(kg.o.internlabs.core.R.drawable.ic_favorite)
                }
            }
        }

        private fun setPager(minifyImages: List<String>?, binding: CardViewMainAdsBinding) =
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
                indicator.attachToPager(imgAds)
                indicator.attachToPager(imgAds)
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

        private fun setOldPriceWithCurrency(item: ResultX) = with(binding) {
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

        private fun placeAndDelivery(item: ResultX) =
            if (item.delivery == true) {
                fragmentContext?.requireActivity()
                    ?.getString(kg.o.internlabs.core.R.string.delivery_available)
                    ?.let { String.format(it, item.location?.name) }
            } else {
                item.location?.name
            }

        private fun Int.formatDecimalSeparator(): String {
            return toString()
                .reversed()
                .chunked(3)
                .joinToString(" ")
                .reversed()
        }

        private fun setSomPrice(price: String?): SpannableString {
            val resultString = String.format(
                getString(coreString.som_underline),
                price?.toInt()?.formatDecimalSeparator()
            )
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
    }

}

object SimilarAdsComparator : DiffUtil.ItemCallback<ResultX>() {
    override fun areItemsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
        return oldItem == newItem
    }
}