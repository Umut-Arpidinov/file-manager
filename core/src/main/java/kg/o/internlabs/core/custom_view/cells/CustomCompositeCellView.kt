package kg.o.internlabs.core.custom_view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CompositeCellBinding

class CustomCompositeCellView : ConstraintLayout {
    private val binding = CompositeCellBinding.inflate(
        LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomCompositeCellView).run {

            setIcon(getResourceId(R.styleable.CustomCompositeCellView_setIcon, 0))

            getString(R.styleable.CustomCompositeCellView_setDate)?.let {
                setDate(it)
            }

            getString(R.styleable.CustomCompositeCellView_setDetails)?.let {
                setDetails(it)
            }

            recycle()
        }
    }

    fun setDetails(details: String) = with(binding.tvCellDetails){
        if (details.isNotEmpty()) {
            isVisible = false
            return
        }
        isVisible = true
        text = details

    }

    fun setDate(title: String) {
        binding.tvCellDate.text = title
    }

    fun setIcon(res: Int) = with(binding.ivCellsIcon){
        if (res == 0) {
            isVisible = false
            return
        }
        isVisible = true
        setImageResource(res)
    }
}