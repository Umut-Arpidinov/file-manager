package kg.o.internlabs.core.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CustomInputFieldBinding
import kg.o.internlabs.core.databinding.CustomInputFieldBinding.inflate
class NumberInputView : ConstraintLayout {
    private val binding: CustomInputFieldBinding = inflate(
        LayoutInflater.from(context), this, true
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        context.obtainStyledAttributes(attributeSet, R.styleable.NumberInputView).run {
            getText(R.styleable.NumberInputView_helperTextState)?.let {
                setHintText(it.toString())
            }
            recycle()
        }
    }

    private fun setHintText(state: String) = with(binding) {
        val numberNotFound = context.getString(R.string.number_mistake)
        val enterNumber = context.getString(R.string.enter_number)
        when (state) {
            numberNotFound -> {
                cancelImageLogic()
                with(numberInputHelperText) {
                    text = numberNotFound
                    setTextColor(ContextCompat.getColor(context, R.color.red_1))
                }
                numberInputCancelImage.visibility = View.VISIBLE
                numberInputFrame.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.number_not_ok_style, null
                )
                enterNumberEditText.background = ResourcesCompat.getDrawable(
                    resources, R.drawable.number_not_ok_style, null
                )
            }
            enterNumber -> {
                numberInputHelperText.text = enterNumber
                numberInputCancelImage.visibility = View.INVISIBLE
            }
        }
    }

    private fun cancelImageLogic() = with(binding) {
        val enterNumber = context.getString(R.string.enter_number)
        numberInputCancelImage.setOnClickListener {
            numberInputHelperText.text = enterNumber
            numberInputCancelImage.visibility = View.GONE
            numberInputFrame.background = ResourcesCompat.getDrawable(
                resources, R.drawable.number_ok_style, null
            )
            enterNumberEditText.background = ResourcesCompat.getDrawable(
                resources, R.drawable.number_ok_style, null
            )
            numberInputHelperText.setTextColor(ContextCompat.getColor(context, R.color.black_1))
            enterNumberEditText.text = null
        }
    }
}