package kg.o.internlabs.core.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.ProgressBtnViewBinding

class ProgressButtonView : ConstraintLayout {
    var textOfView: String = ""
        set(value) {
            field = value
            binding.customTxt.text = value
        }

    private val binding: ProgressBtnViewBinding = ProgressBtnViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.ProgressButtonView).run {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressButtonView)
            val indexes = typedArray.indexCount

            getText(R.styleable.ProgressButtonView_text)?.let {
                for (i in 0..indexes) {
                    val attr = typedArray.getIndex(0)
                    if (attr == R.styleable.ProgressButtonView_text) {
                        textOfView = typedArray.getString(attr).toString()
                    }
                }
            }
            typedArray.recycle()
            recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //button is touched
    fun buttonActivated() {
        binding.customTxt.visibility = GONE
        binding.progressBar.visibility = VISIBLE
    }

    //when process is finished
    fun buttonFinished() {
        binding.progressBar.visibility = GONE
        binding.customTxt.visibility = VISIBLE
    }

    //button clickable or not
    fun buttonAvailability(state: Boolean) {
        if (state) binding.customTxt.setBackgroundResource(R.color.green_1)
        else binding.customTxt.setBackgroundResource(R.color.green_3)
    }
}