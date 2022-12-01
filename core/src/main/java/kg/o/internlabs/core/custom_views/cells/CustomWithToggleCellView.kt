package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.o.internlabs.core.R
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomWithToggleCellViewClick
import kg.o.internlabs.core.databinding.CustomCellWithToggleBinding

class CustomWithToggleCellView : ConstraintLayout {
    private var toggleClicked: CustomWithToggleCellViewClick? = null
    private var positionOfCell: Int = 0

    private val binding = CustomCellWithToggleBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomWithToggleCellView).run {

            getString(R.styleable.CustomWithToggleCellView_setTitle)?.let {
                setTitle(it)
            }

            initListeners()
            recycle()
        }
    }

    private fun initListeners() {
        binding.btnSwitch.setOnClickListener {
            toggleClicked?.toggleClicked(positionOfCell)
        }
    }

    fun setInterface(toggleCellViewClick: CustomWithToggleCellViewClick, positionOfCell: Int = 0) {
        toggleClicked = toggleCellViewClick
        this.positionOfCell = positionOfCell
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun getTitle() = binding.tvTitle.text.toString()
}
