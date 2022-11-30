package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CompositeCellBinding

class CustomCompositeCellView : ConstraintLayout {
    private val binding = CompositeCellBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private var imageResources = 0
    private var date = ""
    private var details = ""

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

    fun getIcon() = imageResources

    fun getDate() = date

    fun getDetails() = details

    //fun getComments() = binding.etComment.text.toString()

    fun setDetails(details: String) {

    }

    fun setDate(date: String) {

    }

    fun setIcon(@DrawableRes res: Int)  {

    }
}