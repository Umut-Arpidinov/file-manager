package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.ProfileCellViewBinding

class CustomProfileCellView : ConstraintLayout {
    private val binding = ProfileCellViewBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomProfileCellView).run {

            getString(R.styleable.CustomProfileCellView_setIcon)?.let {
                setIcon(it)
            }

            getString(R.styleable.CustomProfileCellView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomProfileCellView_setSubtitle)?.let {
                setSubtitle(it)
            }

            isShevronVisible(getBoolean(R.styleable.CustomProfileCellView_isShevronVisible, false))

            recycle()
        }
    }

    fun isShevronVisible(isShevronVisible: Boolean) {
        binding.ivShevron.isVisible = isShevronVisible
    }

    fun setSubtitle(subtitle: String) = with(binding.tvCellSubtitle) {
        isVisible = subtitle.isNotEmpty()
        text = subtitle
    }

    fun setTitle(title: String) {
        binding.tvCellTitle.text = title
    }

    fun setIcon(res: String) = with(binding.ivCellsIcon){
        isVisible = res.isNotEmpty()
        val uri: Uri = Uri.parse(res)
       // Glide.with(context).load(uri).centerCrop().into(this)
    }
}