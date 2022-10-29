package kg.o.internlabs.core.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.SubtitleTextCellBinding

class CustomBigSubtitleTextCellsView : ConstraintLayout {
    private val binding = SubtitleTextCellBinding.inflate(LayoutInflater.from(context),
        this, true)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs,
            R.styleable.CustomSmallSubtitleTextCellsView).run {
                getString(R.styleable.CustomSmallSubtitleTextCellsView_position)?.let {
                    setBackground(it)
                }

                getBoolean(
                    R.styleable.CustomSmallSubtitleTextCellsView_hasIcon,
                    false
                ).let {
                    hasIcon(it)
                    if (it) {
                        setIcon(
                            getResourceId(
                                R.styleable.CustomSmallSubtitleTextCellsView_setIcon,
                                R.drawable.bg_cells_image
                            )
                        )
                    }
                }

                getString(R.styleable.CustomSmallSubtitleTextCellsView_setTitle)?.let {
                    setTitle(it)
                }

                getBoolean(R.styleable.CustomSmallSubtitleTextCellsView_hasSubtitle, false).let {
                    hasVisibility(it)
                    if (it) {
                        getString(R.styleable.CustomSmallSubtitleTextCellsView_setSubtitle)?.let { subtitle ->
                            setSubtitle(subtitle)
                        }
                    }
                }

                getBoolean(R.styleable.CustomSmallSubtitleTextCellsView_hasDetails, false).let {
                    hasDetails(it)
                    if (it) {
                        getString(R.styleable.CustomSmallSubtitleTextCellsView_setDetails)?.let { details ->
                            setDetails(details)
                        }
                    }
                }

                getBoolean(R.styleable.CustomSmallSubtitleTextCellsView_hasShevron, false).let {
                    hasShevron(it)
                    if (it) {
                        setShevron(
                            getResourceId(
                                R.styleable.CustomSmallSubtitleTextCellsView_setShevron,
                                R.drawable.arrow_shevron
                            )
                        )
                    }
                }

            recycle()
        }
    }

     fun setSubtitle(subtitle: String) {
        binding.tvCellSubtitle.text = subtitle
    }

    fun hasVisibility(it: Boolean) {
        binding.tvCellSubtitle.isVisible = it
    }

    fun hasShevron(it: Boolean) {
        binding.ivShevron.isVisible = it
    }

    fun hasDetails(it: Boolean) {
        binding.tvCellDetails.isVisible = it
    }

    fun hasIcon(visibility: Boolean) {
        binding.ivCellsIcon.isVisible = visibility
    }

    fun setShevron(resourceId: Int) {
        binding.ivShevron.setImageResource(resourceId)
    }

    fun setDetails(details: String) {
        binding.tvCellDetails.text = details

    }

    fun setTitle(title: String) {
        binding.tvCellTitle.text = title
    }

    fun setIcon(res: Int) {
        binding.ivCellsIcon.setImageResource(res)
    }

    fun setBackground(pos: String) = with(binding){
        when(pos) {
            "Single" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_around_corners, null)
            }
            "Top" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_top_corners, null)
                vDivider.isVisible = true
            }
            "Bottom" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_bottom_corners, null)
            }
            "Middle" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_middle_bacground, null)
                vDivider.isVisible = true
            }
        }
    }
}