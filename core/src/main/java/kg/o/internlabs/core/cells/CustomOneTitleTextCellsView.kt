package kg.o.internlabs.core.cells

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

            getResourceId(R.styleable.CustomOneTitleTextCellsView_setIcon, 0).let {
                setIcon(it)
            }

            getString(R.styleable.CustomOneTitleTextCellsView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomOneTitleTextCellsView_setDetails)?.let { details ->
                setDetails(details)
            }


            getBoolean(R.styleable.CustomOneTitleTextCellsView_hasShevron, false).let {
                hasShevron(it)
                if (it) {
                    setShevron(
                        getResourceId(
                            R.styleable.CustomOneTitleTextCellsView_setShevron,
                            R.drawable.arrow_shevron
                        )
                    )
                }
            }
            recycle()
        }
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

    fun setIcon(res: Int) = with(binding) {
        if (res == 0) {
            ivCellsIcon.isVisible = false
        }
        ivCellsIcon.isVisible = true
        ivCellsIcon.setImageResource(res)
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
                    resources, R.drawable.cell_middle_bacground, null
                )
                vDivider.isVisible = true
            }
        }
    }
}