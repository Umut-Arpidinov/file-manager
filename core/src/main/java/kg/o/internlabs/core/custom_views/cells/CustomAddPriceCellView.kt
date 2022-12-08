package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kg.o.internlabs.core.R
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomAddPriceCellViewClick
import kg.o.internlabs.core.databinding.AddPriceCellBinding

class CustomAddPriceCellView : ConstraintLayout {

    private var aboutAdvertiser: CustomAddPriceCellViewClick? = null

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
                R.styleable.CustomAddPriceCellView_isOMoneyAccepted, false))

            isNumberVerified(getBoolean(
                R.styleable.CustomAddPriceCellView_isNumberVerified, false))

            getString(R.styleable.CustomAddPriceCellView_setImage)?.let {
                setIcon(it)
            }

            setIcon(getResourceId(R.styleable.CustomAddPriceCellView_setImage, 0))

            initListener()

            recycle()
        }
    }

    private fun initListener() {
        binding.ivShevron.setOnClickListener {
            aboutAdvertiser?.advertiserClicked()
        }
    }

    fun isNumberVerified(isVerified: Boolean) {
        binding.tvNumberVerified.isVisible = isVerified
    }

    fun isODengiAccepted(accept: Boolean) {
        binding.tvOMoneyAccepted.isVisible = accept
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

    fun setIcon(uri: String): Unit = with(binding.ivCellsIcon) {
        if (uri.startsWith("res")) return
        val mUri: Uri = Uri.parse(uri)
        Glide.with(context).load(mUri).centerCrop().into(this)
    }

    fun setIcon(uri: Int): Unit = with(binding.ivCellsIcon){
        if (uri == 0) return
        Glide.with(context).load(uri)
            .centerInside()
            .placeholder(ResourcesCompat.getDrawable(resources, R.drawable.ic_small_profile, null))
            .centerCrop()
            .into(this)
    }

    fun isNumberVerified() = binding.tvNumberVerified.isVisible

    fun isOMoneyAccepted() = binding.tvOMoneyAccepted.isVisible

    fun getPrice() = with(binding){
        tvPriceWithoutCoins.text.toString() + tvPriceWithCoins.text.toString()
    }

    fun getTitle() = binding.tvCellTitle.text.toString()

    fun setInterface(addPriceClick: CustomAddPriceCellViewClick) {
        aboutAdvertiser = addPriceClick
    }
}