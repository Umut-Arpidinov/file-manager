package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.BigSubtitleTextCellBinding

class CustomBigSubtitleTextCellsView : ConstraintLayout {
    private val binding = BigSubtitleTextCellBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomBigSubtitleTextCellsView).run {

            editOrDelete(getBoolean(R.styleable.CustomBigSubtitleTextCellsView_isEditable, false))

            getString(R.styleable.CustomBigSubtitleTextCellsView_position)?.let {
                setBackground(it)
            }
            setIcon(getResourceId(R.styleable.CustomBigSubtitleTextCellsView_setIcon, 0))

            getString(R.styleable.CustomBigSubtitleTextCellsView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomBigSubtitleTextCellsView_setSubtitle)?.let {
                setSubtitle(it)
            }

            getString(R.styleable.CustomBigSubtitleTextCellsView_setDetails)?.let {
                setDetails(it)
            }

            setShevron(getResourceId(R.styleable.CustomBigSubtitleTextCellsView_setShevron, 0))

            recycle()
        }
    }

    fun editOrDelete(it: Boolean) = with(binding){
        if (it) return
        ivCellsEditIcon.isVisible = false
        ivEditDel.isVisible = false
    }

    fun setSubtitle(subtitle: String) = with(binding.tvCellSubtitle) {
        isVisible = subtitle.isNotEmpty()
        text = subtitle
    }

    fun setShevron(@DrawableRes resourceId: Int) = with(binding.ivShevron){
        isVisible = resourceId != 0
        setImageResource(resourceId)
    }

    fun setDetails(details: String) = with(binding.tvCellDetails){
        isVisible = details.isNotEmpty()
        text = details

    }

    fun setTitle(title: String) {
        binding.tvCellTitle.text = title
    }

    fun setIcon(@DrawableRes res: Int) = with(binding.ivCellsIcon){
        isVisible = res != 0
        setImageResource(res)
    }

    fun setBackground(pos: String) = with(binding) {
        when (pos) {
            "Single" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_around_corners, null
                )
            }
            "Top" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_top_corners, null
                )
                vDivider.isVisible = true
            }
            "Bottom" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_bottom_corners, null
                )
            }
            "Middle" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_middle_background, null
                )
                vDivider.isVisible = true
            }
        }
    }
}