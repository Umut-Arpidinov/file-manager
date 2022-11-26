package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.RoundedOneTitleTextCellBinding

class CustomRoundedOneTitleTextCellsView : ConstraintLayout {
    private val binding: RoundedOneTitleTextCellBinding = RoundedOneTitleTextCellBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomRoundedOneTitleTextCellsView).run {

            setPosition(
                convertToEnum(
                    getInt(
                        R.styleable.CustomRoundedOneTitleTextCellsView_position,
                        0
                    )
                )
            )

            setIcon(getResourceId(R.styleable.CustomRoundedOneTitleTextCellsView_setIcon, 0))

            getString(R.styleable.CustomRoundedOneTitleTextCellsView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomRoundedOneTitleTextCellsView_setInfo)?.let {
                setInfo(it)
            }

            setSimpleCell(
                getBoolean(
                    R.styleable.CustomRoundedOneTitleTextCellsView_isSimpleCell,
                    false
                )
            )

            setShevron(
                getBoolean(
                    R.styleable.CustomRoundedOneTitleTextCellsView_isShevronVisible,
                    false
                )
            )
            recycle()
        }
    }

    private fun convertToEnum(index: Int) = when (index) {
        0 -> Position.SINGLE
        1 -> Position.TOP
        2 -> Position.MIDDLE
        else -> Position.BOTTOM
    }

    fun setInfo(info: String) {
        binding.tvCellInfo.text = info
    }

    fun setSimpleCell(isSimpleCellSet: Boolean) = with(binding) {
        clNoIcon.isVisible = isSimpleCellSet
        clWithIcon.isVisible = isSimpleCellSet.not()
    }

    fun setShevron(isVisible: Boolean) {
        binding.ivShevron.isVisible = isVisible
    }

    fun setTitle(title: String) = with(binding) {
        if (clNoIcon.isVisible) {
            tvCellTitleGray.text = title
            return
        }
        tvCellTitle.text = title
    }

    fun setIcon(res: Int) = with(binding.ivCellsIcon) {
        if (res == 0) {
            isVisible = false
            return
        }
        isVisible = true
        setImageResource(res)
    }


    fun setPosition(pos: Position) = with(binding) {
        when (pos) {
            Position.SINGLE -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_around_corners, null
                )
            }
            Position.TOP -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_top_corners, null
                )
                vDivider.isVisible = true
            }
            Position.BOTTOM -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_bottom_corners, null
                )
            }
            Position.MIDDLE -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_middle_background, null
                )
                vDivider.isVisible = true
            }
        }
    }
}