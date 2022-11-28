package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.AddPriceCellBinding

class CustomAddPriceCellView : ConstraintLayout {
    private val binding = AddPriceCellBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomAddPriceCellView
        ).run {
            getString(R.styleable.CustomAddPriceCellView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomAddPriceCellView_setPriceWithoutCoins)?.let {
                setPriceWithoutCoins(it)
            }

            getString(R.styleable.CustomAddPriceCellView_setPriceWithCoins)?.let {
                setPriceWithCoins(it)
            }

            isODengiAccepted(getBoolean(
                R.styleable.CustomAddPriceCellView_isODengiAccepted, false))

            isNumberVerified(getBoolean(
                R.styleable.CustomAddPriceCellView_isNumberVerified, false))

            getString(R.styleable.CustomAddPriceCellView_setIcon)?.let {
                setIcon(it)
            }

            recycle()
        }
    }

    fun isNumberVerified(isVerified: Boolean) {
        binding.tvNumberVerified.isVisible = isVerified
    }

    fun isODengiAccepted(accept: Boolean) {
        binding.tvODengiAccepted.isVisible = accept
    }

    fun setPriceWithoutCoins(price: String) {
        binding.tvPriceWithoutCoins.text = price
    }

    fun setPriceWithCoins(price: String) {
        binding.tvPriceWithCoins.text = price
    }

    fun setTitle(title: String) {
        binding.tvCellTitle.text = title
    }

    fun setIcon(uri: String) = with(binding.ivCellsIcon) {
        val mUri: Uri = Uri.parse(uri)
       // Glide.with(context).load(mUri).centerCrop().into(this)
    }
}