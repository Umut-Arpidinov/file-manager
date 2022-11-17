package kg.o.internlabs.core.custom_views

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
class CustomNumberInputView : ConstraintLayout {
    private val binding: CustomInputFieldBinding = inflate(
        LayoutInflater.from(context), this, true
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        context.obtainStyledAttributes(attributeSet, R.styleable.CustomNumberInputView).run {
            getText(R.styleable.CustomNumberInputView_helperTextState)?.let {
                setMessage(it.toString())
            }
            recycle()
            initClick()
        }
    }

    fun setInterface(textWatcher: NumberInputHelper, fieldsNumber: Int = 0) {
        binding.enterNumberEditText.setInterface(textWatcher, fieldsNumber)
    }

    fun getVales() = binding.enterNumberEditText.getValue()
    fun setValue(message: String){
        binding.enterNumberEditText.setValue(message.substringAfter(' '))
    }

    fun setMessage(message: String) = with(binding){
        numberInputHelperText.text = message
        setFrameDefaultColor()
        setTextDefaultColor()
    }

    fun setErrorMessage(message: String){
        setMessage(message)
        setFrameErrorColor()
        setTextErrorColor()
    }
    private fun setFrameDefaultColor() = with(binding){
        numberInputFrame.background = ResourcesCompat.getDrawable(resources,R.drawable.number_ok_style,null)
        enterNumberEditText.background  = ResourcesCompat.getDrawable(resources,R.drawable.number_ok_style,null)

    }
    private fun setTextDefaultColor()=with(binding){
        numberInputHelperText.setTextColor(ContextCompat.getColor(context,R.color.black_1))
        numberInputHelperText.setTextAppearance(R.style.hint)
    }
    private fun setFrameErrorColor() = with(binding){
        numberInputFrame.background = ResourcesCompat.getDrawable(
            resources, R.drawable.number_not_ok_style, null
        )
        enterNumberEditText.background = ResourcesCompat.getDrawable(
            resources, R.drawable.number_not_ok_style, null
        )
    }
    private fun setTextErrorColor() = with(binding){
        numberInputHelperText.setTextColor(ContextCompat.getColor(context, R.color.red_1))
    fun setHintText(state: String) = with(binding) {
        val numberNotFound = context.getString(R.string.number_mistake)
        val numberNotExists = context.getString(R.string.number_not_Exists)
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
            numberNotExists -> {
                cancelImageLogic()
                with(numberInputHelperText) {
                    text = numberNotExists
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
    private fun initClick() = with(binding){
        numberInputCancelImage.setOnClickListener {
            setMessage("Введите номер телефона")
            numberInputHelperText.setTextAppearance(R.style.hint)
            enterNumberEditText.eraseField()

        }
    }
}