package kg.o.internlabs.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kg.o.internlabs.core.databinding.CustomInputFieldBinding
import kg.o.internlabs.core.databinding.CustomInputFieldBinding.inflate

class NumberInputView : ConstraintLayout {
    private val binding: CustomInputFieldBinding = inflate(LayoutInflater.from(context), this, true)
    private lateinit var numberInputHelper: TextView
    private lateinit var cancelImage: ImageView

    constructor(context: Context) : super(context)


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        context.obtainStyledAttributes(attributeSet, R.styleable.NumberInputView).run {
            getText(R.styleable.NumberInputView_android_text)?.let {
                setHintText(it.toString())
            }

            setCancelImageVisibility(
                getBoolean(
                    R.styleable.NumberInputView_cancelImageVisibility,
                    true
                )
            )
            recycle()
        }

    }


    init {
        initView()
    }

    private fun initView() {
        numberInputHelper = binding.numberInputHelperText
        cancelImage = binding.numberInputCancelImage
    }

    private fun setHintText(text: String) {
        numberInputHelper.text = text
    }

    private fun setCancelImageVisibility(isVisible: Boolean) {
        when (isVisible) {
            true -> cancelImage.visibility = View.VISIBLE
            else -> cancelImage.visibility = View.GONE
        }
    }

}
