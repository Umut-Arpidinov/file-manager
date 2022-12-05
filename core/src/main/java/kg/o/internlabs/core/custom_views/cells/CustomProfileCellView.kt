package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kg.o.internlabs.core.R
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomProfileCellViewClickers
import kg.o.internlabs.core.databinding.ProfileCellViewBinding

class CustomProfileCellView : ConstraintLayout {

    private var clickers: CustomProfileCellViewClickers? = null

    private val binding = ProfileCellViewBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomProfileCellView).run {

            getString(R.styleable.CustomProfileCellView_setImage)?.let {
                setIcon(it)
            }

            getString(R.styleable.CustomProfileCellView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomProfileCellView_setSubtitle)?.let {
                setSubtitle(it)
            }

            isShevronVisible(getBoolean(R.styleable.CustomProfileCellView_isShevronVisible, false))

            setIcon(getResourceId(R.styleable.CustomProfileCellView_setImage, 0))

            initClickers()
            recycle()
        }
    }

    private fun initClickers(): Unit = with(binding.ivCellsIcon){
        setOnClickListener {
            clickers?.iconClick()
        }

        setOnLongClickListener {
            clickers?.iconLongClick()
            return@setOnLongClickListener true
        }
    }

    fun isShevronVisible(isShevronVisible: Boolean) {
        binding.ivShevron.isVisible = isShevronVisible
    }

    fun setSubtitle(subtitle: String): Unit = with(binding.tvCellSubtitle) {
        isVisible = subtitle.isNotEmpty()
        text = subtitle
    }

    fun setTitle(title: String) {
        binding.tvCellTitle.text = title
    }

    fun setIcon(uri: String): Unit = with(binding.ivCellsIcon) {
        if (uri.startsWith("res")) return
        val mUri: Uri = Uri.parse(uri)
        Glide.with(context).load(mUri).centerCrop().into(this)
    }

    fun setIcon(res: Int): Unit = with(binding.ivCellsIcon){
        if (res == 0) return
        Glide.with(context).load(res)
            .centerCrop()
            .into(this)
    }

    fun setInterface(clickers: CustomProfileCellViewClickers) {
        this.clickers = clickers
    }

    fun getTitle() = binding.tvCellTitle.text.toString()

    fun getSubTitle() = binding.tvCellSubtitle.text.toString()
}