package kg.o.internlabs.core

import android.content.Context

import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kg.o.internlabs.core.databinding.CustomInputFieldBinding
import kg.o.internlabs.core.databinding.CustomInputFieldBinding.inflate

class NumberInputView : ConstraintLayout {
    private val binding: CustomInputFieldBinding = inflate(LayoutInflater.from(context), this, true)


    private lateinit var numberInputHelper: TextView
    private lateinit var cancelImage: ImageView
    private lateinit var frameLayout: FrameLayout
    private lateinit var numberInputEditText: EditText
    constructor(context: Context) : super(context)


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        context.obtainStyledAttributes(attributeSet, R.styleable.NumberInputView).run {
            getText(R.styleable.NumberInputView_helperTextState)?.let {
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
        frameLayout = binding.numberInputFrame
        numberInputEditText = binding.enterNumberEditText
    }

    private fun setHintText(state: String) {
        val numberNotFound = context.getString(R.string.Ошибка_номера)
        val enterNumber = context.getString(R.string.Ввод)
        when(state){
            numberNotFound -> {
                numberInputHelper.text = numberNotFound
                numberInputHelper.setTextColor(ContextCompat.getColor(context,R.color.red_1))
                frameLayout.background = ResourcesCompat.getDrawable(resources,R.drawable.number_not_ok_style,null)
                numberInputEditText.background = ResourcesCompat.getDrawable(resources,R.drawable.number_not_ok_style,null)


            }
            enterNumber ->{
                numberInputHelper.text = enterNumber
            }

        }
    }

    private fun setCancelImageVisibility(isVisible: Boolean) {
        when (isVisible) {
            true -> cancelImage.visibility = View.VISIBLE
            else -> cancelImage.visibility = View.GONE
        }
    }

}
