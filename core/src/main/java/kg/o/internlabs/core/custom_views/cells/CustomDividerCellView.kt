package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
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

            recycle()
        }
    }

    private fun initClick() = with(binding){
        //btnDetails.setOnClickListener {
           // TODO click
        }
    }

    fun setButtonText(textInButton: String) {

    }

    fun setTitle(title: String) {

    }
