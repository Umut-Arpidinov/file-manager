package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.OneTitleTextCellBinding

class CustomOneTitleTextCellsView : ConstraintLayout {
    private val binding: OneTitleTextCellBinding = OneTitleTextCellBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomOneTitleTextCellsView).run {

            getString(R.styleable.CustomOneTitleTextCellsView_position)?.let {
                setBackground(it)
            }

            setIcon(getResourceId(R.styleable.CustomOneTitleTextCellsView_setIcon, 0))

            getString(R.styleable.CustomOneTitleTextCellsView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomOneTitleTextCellsView_setDetails)?.let {
                setDetails(it)
            }

            getResourceId(
                R.styleable.CustomOneTitleTextCellsView_setShevron,
                0
            ).let {
                setShevron(it)
            }
            recycle()
        }
    }

    fun setShevron(resourceId: Int) = with(binding.ivShevron) {
        if (resourceId == 0) {
            isVisible = false
            return
        }
        isVisible = true
        setImageResource(resourceId)
    }

    fun setDetails(details: String) = with(binding.tvCellDetails) {
        if (details.isEmpty()) {
            isVisible = false
            return
        }
        isVisible = true
        text = details
    }

    fun setTitle(title: String) {
        binding.tvCellTitle.text = title
    }

    fun setIcon(res: Int) = with(binding.ivCellsIcon) {
        if (res == 0) {
            isVisible = false
            return
        }
        isVisible = true
        setImageResource(res)
    }

    fun setBackground(pos: String) = with(binding) {
        when (pos) {
            "Single" -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_around_corners, null
                )
                vDivider.isVisible = false
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
                vDivider.isVisible = false
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