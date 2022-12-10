package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.graphics.Paint
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.data.remote.model.MainAdsDto
import kg.o.internlabs.omarket.databinding.CardViewMainAdsBinding

class AdsListAdapter internal constructor(
    var list: List<MainAdsDto>,
    private val itemWidth: Int,
    val context: MainFragment
) :
    RecyclerView.Adapter<AdsListAdapter.ViewHolder>() {
    private var favorite = true

    class ViewHolder(val binding: CardViewMainAdsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardViewMainAdsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.cardViewAds.layoutParams.width = itemWidth

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mList = list[position]

        with(holder.binding) {
            val pagerAdapter2 =
                PagerImageAdapter(context, mList.minifyImages, ViewGroup.LayoutParams.MATCH_PARENT)
            imgAds.adapter = pagerAdapter2
            indicator.attachToPager(imgAds)
            holder.binding.indicator.attachToPager(holder.binding.imgAds)

            isVIPStatus(mList.isVIP, vipIcon)
            isOMoney(mList.isO_pay, oPayIcon)

            priceProduct.text = setPriceWithCurrency(mList.currency, mList.price)
            val spannableString2 = SpannableString(priceProduct.text)
            spannableString2.setSpan(UnderlineSpan(), priceProduct.text.lastIndex, priceProduct.text.length, 0)
            priceProduct.text = spannableString2

            oldPriceProduct.apply {
                if (mList.oldPrice == null || mList.oldPrice == "") {
                    visibility = GONE
                } else {
                    visibility = VISIBLE
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    text = setPriceWithCurrency(mList.currency, mList.oldPrice)
                }
            }

            nameProduct.text = mList.name?.capitalize()
            nameCategory.text = mList.category?.capitalize()

            if (mList.delivery == true) {
                var mText = mList.location + "/Доставка"
                placeProduct.text = mText
            } else {
                placeProduct.text = mList.location
            }

            favoriteIcon.setOnClickListener{
                if (mList.favorite == false || mList.favorite == null && !favorite) {
                    favoriteIcon.setImageResource(kg.o.internlabs.core.R.drawable.ic_favorite_pressed)
                    favorite = true
                } else {
                    favorite = false
                    favoriteIcon.setImageResource(kg.o.internlabs.core.R.drawable.ic_favorite)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun isVIPStatus(status: Boolean?, vipIcon: AppCompatImageView) {
        if (status != null) {
            if (status) vipIcon.visibility = View.VISIBLE else vipIcon.visibility = View.GONE
        } else vipIcon.visibility = View.GONE
    }

    private fun isOMoney(status: Boolean?, oPayIcon: AppCompatImageView) {
        if (status != null) {
            if (status) oPayIcon.visibility = View.VISIBLE else oPayIcon.visibility = View.GONE
        } else oPayIcon.visibility = View.GONE
    }

    private fun setPriceWithCurrency(currency: String?, price: String?): String {
        var result = String.format("%,d", price?.toInt())
        //add Underline
        if (currency == "som") {
            result += " c"
        } else result += "$"

        return result
    }
}