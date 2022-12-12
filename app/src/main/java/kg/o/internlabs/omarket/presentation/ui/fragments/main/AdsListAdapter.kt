/*
package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.graphics.Paint
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.data.remote.model.ResultX
import kg.o.internlabs.omarket.databinding.CardViewMainAdsBinding
import kg.o.internlabs.omarket.domain.entity.ResultEntity
import java.util.*

private typealias coreString = kg.o.internlabs.core.R.string


class AdsListAdapter internal constructor(
    var list: MutableList<ResultX>?,
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

        val mList = list?.get(position)



        with(holder.binding) {

            val pagerAdapter2 =
                PagerImageAdapter(context, mList, ViewGroup.LayoutParams.MATCH_PARENT)
            imgAds.adapter = pagerAdapter2
            indicator.attachToPager(imgAds)
            holder.binding.indicator.attachToPager(holder.binding.imgAds)

            isVIPStatus(, vipIcon)

            isOMoney(mList.isO_pay, oPayIcon)

            setPriceWithCurrency(mList.currency, mList.price, priceProduct)

            setOldPriceWithCurrency(mList.currency, mList.oldPrice, oldPriceProduct)

            nameProduct.text = mList.name?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }

            nameCategory.text = mList.category?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }

            defineDelivery(mList.delivery, placeProduct, mList.location)

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
            if (status) vipIcon.visibility = VISIBLE else vipIcon.visibility = GONE
        } else vipIcon.visibility = GONE
    }

    private fun isOMoney(status: Boolean?, oPayIcon: AppCompatImageView) {
        if (status != null) {
            if (status) oPayIcon.visibility = VISIBLE else oPayIcon.visibility = GONE
        } else oPayIcon.visibility = GONE
    }

    private fun setPriceWithCurrency(currency: String?, price: String?, priceProduct: TextView) {
        if (currency.equals("som")){
            val resultString = String.format(getString(coreString.som_underline), price?.toInt())

            val spannableString = SpannableString(resultString)
            spannableString.setSpan(UnderlineSpan(), resultString.lastIndex, resultString.length, 0)

            priceProduct.text = spannableString
        } else
            priceProduct.text = String.format(getString(coreString.dollar_price), price?.toInt())
    }

    private fun setOldPriceWithCurrency(currency: String?, oldPrice: String?, oldPriceProduct: TextView) {
        if (oldPrice == null || oldPrice == "") {
            oldPriceProduct.visibility = GONE
        } else {
            oldPriceProduct.visibility = VISIBLE
            oldPriceProduct.paintFlags = oldPriceProduct.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            setPriceWithCurrency(currency, oldPrice, oldPriceProduct)
        }
    }
    private fun defineDelivery(delivery: Boolean?, placeProduct: TextView, location: String?) {
        if (delivery == true)
            placeProduct.text = String.format(getString(coreString.delivery_available), location)
        else
            placeProduct.text = location
    }

    private fun getString(@StringRes resId: Int): String {
        return context.resources.getString(resId)
    }
}*/
