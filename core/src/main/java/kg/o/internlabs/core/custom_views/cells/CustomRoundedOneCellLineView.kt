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
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomRoundedOneCellLineViewClick
import kg.o.internlabs.core.databinding.RoundedOneCellLineBinding

class CustomRoundedOneCellLineView : ConstraintLayout {

    private var buttonClicked: CustomRoundedOneCellLineViewClick? = null
    private var positionOfCell = 0

    private val binding: RoundedOneCellLineBinding = RoundedOneCellLineBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomRoundedOneCellLineView).run {

            isSimpleCell(
                getBoolean(
                    R.styleable.CustomRoundedOneCellLineView_isSimpleCell,
                    false
                )
            )

            setPosition(
                convertToEnum(
                    getInt(
                        R.styleable.CustomRoundedOneCellLineView_position,
                        0
                    )
                )
            )

            setIcon(getResourceId(R.styleable.CustomRoundedOneCellLineView_setIcon, 0))

            getString(R.styleable.CustomRoundedOneCellLineView_setIcon)?.let {
                setIcon(it)
            }

            hasIcon(
                getBoolean(
                    R.styleable.CustomRoundedOneCellLineView_hasIcon, false
                )
            )

            getString(R.styleable.CustomRoundedOneCellLineView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomRoundedOneCellLineView_setInfo)?.let {
                setInfo(it)
            }

            isRadioButtonVisible(
                getBoolean(
                    R.styleable.CustomRoundedOneCellLineView_isRadioButtonVisible, false
                )
            )

            shevronVisibility(
                getBoolean(
                    R.styleable.CustomRoundedOneCellLineView_isShevronVisible,
                    false
                )
            )

            initListener()
            recycle()
        }
    }

    fun hasIcon(hasIcon: Boolean) {
        binding.cv.isVisible = hasIcon
    }

    private fun initListener() {
        binding.radioButton.setOnClickListener {
            buttonClicked?.buttonClicked(positionOfCell)
        }
    }

    fun isRadioButtonVisible(boolean: Boolean): Unit = with(binding) {
        radioButton.isVisible = boolean
        if (radioButton.isVisible) {
            ivShevron.isVisible = false
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

    fun isSimpleCell(isSimpleCellSet: Boolean) = with(binding) {
        clNoIcon.isVisible = isSimpleCellSet
        clWithIcon.isVisible = isSimpleCellSet.not()
    }

    fun shevronVisibility(isVisible: Boolean): Unit = with(binding) {
        ivShevron.isVisible = isVisible
        if (ivShevron.isVisible) {
            radioButton.isVisible = false
        }
    }

    fun setTitle(title: String) = with(binding) {
        if (clNoIcon.isVisible) {
            tvCellTitleGray.text = title
            return
        }
        tvCellTitle.text = title
    }

    fun setIcon(uri: String): Unit = with(binding.ivCellsIcon) {
        if (uri.startsWith("res")) return
        isVisible = true
        val mUri: Uri = Uri.parse(uri)
        Glide.with(context).load(mUri).centerCrop().into(this)
    }

    fun setIcon(res: Int): Unit = with(binding.ivCellsIcon) {
        if (res == 0) return
        isVisible = true

        Glide.with(context).load(res)
            .centerCrop()
            .into(this)
    }

    fun setInterface(buttonClicked: CustomRoundedOneCellLineViewClick, positionOfCell: Int = 0) {
        this.buttonClicked = buttonClicked
        this.positionOfCell = positionOfCell
    }

    fun setPosition(pos: Position) = with(binding) {
        when (pos) {
            Position.SINGLE -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_around_corners, null
                )
                vDivider.isVisible = false
                vDivider2.isVisible = false
            }
            Position.TOP -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_top_corners, null
                )
                vDivider.isVisible = clWithIcon.isVisible
                vDivider2.isVisible = clNoIcon.isVisible
            }
            Position.BOTTOM -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_bottom_corners, null
                )
                vDivider.isVisible = false
                vDivider2.isVisible = false
            }
            Position.MIDDLE -> {
                root.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.cell_middle_background, null
                )
                vDivider.isVisible = clWithIcon.isVisible
                vDivider2.isVisible = clNoIcon.isVisible
            }
        }
    }

    fun getInfo() = binding.tvCellInfo.text.toString()

    fun getTitle() = binding.tvCellTitle.text.toString()

    fun getGrayTitle() = binding.tvCellTitleGray.text.toString()
}