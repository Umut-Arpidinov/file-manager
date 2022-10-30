package kg.o.internlabs.core.custom_view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.DividerCellBinding

class CustomDividerCellView : ConstraintLayout {
    private val binding = DividerCellBinding.inflate(
        LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomDividerCellView).run {

            getString(R.styleable.CustomDividerCellView_setTitle)?.let {
                setTitle(it)
            }

            getString(R.styleable.CustomDividerCellView_setButton)?.let {
                setButtonText(it)
            }
            recycle()
        }
    }

    fun setButtonText(textInButton: String) = with(binding.tvCellDetails){
        if (textInButton.isNotEmpty()) {
            isVisible = false
            return
        }
        isVisible = true
        text = textInButton

    }

    fun setTitle(title: String) {
        binding.tvCellDate.text = title
    }
}