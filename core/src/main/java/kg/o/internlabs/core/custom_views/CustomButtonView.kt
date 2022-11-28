package kg.o.internlabs.core.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CustomBtnViewBinding

class CustomButtonView : ConstraintLayout {
    var textOfView: String = ""
        set(value) {
            field = value
            binding.customTxt.text = value
        }

    private val binding: CustomBtnViewBinding = CustomBtnViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomButtonView).run {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButtonView)
            val indexes = typedArray.indexCount

            getText(R.styleable.CustomButtonView_text)?.let {
                for (i in 0..indexes) {
                    val attr = typedArray.getIndex(0)
                    if (attr == R.styleable.CustomButtonView_text) {
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
    fun buttonFinished() = with(binding) {
        progressBar.visibility = GONE
        cardViewBtn.isClickable = false
        with(customTxt) {
            visibility = VISIBLE
            isClickable = false
        }
    }

    //button clickable or not
    fun buttonAvailability(state: Boolean) = with(binding) {
        if (state) {
            cardViewBtn.isClickable = false
            with(customTxt) {
                setBackgroundResource(R.color.green_1)
                isClickable = false
            }
        } else {
            with(customTxt) {
                isEnabled = false
                setBackgroundResource(R.color.green_3)
                isClickable = true
            }
        }
    }

    fun buttonAvailability(state: Int = 0) = with(binding) {
        when (state) {
            0 -> {
                cardViewBtn.isClickable = false
                customTxt.isVisible = false
            }
            else -> {
                with(customTxt) {
                    isEnabled = false
                    setBackgroundResource(R.color.green_3)
                }
            }
        }
    }
}