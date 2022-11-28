package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.BigSubtitleTextCellBinding

class CustomTwoCellsLineView : ConstraintLayout {
    private val binding = BigSubtitleTextCellBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomTwoCellsLineView).run {

            setIcon(getResourceId(R.styleable.CustomTwoCellsLineView_setIcon, 0))

            getString(R.styleable.CustomTwoCellsLineView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomTwoCellsLineView_setSubtitle)?.let {
                setSubtitle(it)
            }

            recycle()
        }
    }

    fun setSubtitle(subtitle: String) = with(binding.tvCellSubtitle) {
        isVisible = subtitle.isNotEmpty()
        text = subtitle
    }

    fun setTitle(title: String) {
        binding.tvCellTitle.text = title
    }

    fun setIcon(@DrawableRes res: Int) = with(binding.ivCellsIcon){
        isVisible = res != 0
        Glide.with(context).load(res).centerCrop().into(this)
    }
}