package kg.o.internlabs.core.custom_view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.FAQCellBinding

class CustomFAQCellView : ConstraintLayout {
    private val binding = FAQCellBinding.inflate(
        LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomFAQCellView).run {

            getString(R.styleable.CustomFAQCellView_setQuestions)?.let {
                setQuestions(it)
            }

            getString(R.styleable.CustomFAQCellView_setAnswers)?.let {
                setAnswers(it)
            }
            recycle()
        }
        initClick()
    }

    private fun initClick() = with(binding){
        ivShevron.setOnClickListener {
            llBottom.isVisible = !llBottom.isVisible
            vDivider.isVisible = llBottom.isVisible
            if (llBottom.isVisible){
                ivShevron.setImageResource(R.drawable.ic_arrow_up)
            } else {
                ivShevron.setImageResource(R.drawable.ic_arrow_down)
            }
        }
    }

    fun setQuestions(title: String) {
        binding.tvCellQuestions.text = title
    }

    fun setAnswers(title: String) {
        binding.tvCellAnswer.text = title
    }
}