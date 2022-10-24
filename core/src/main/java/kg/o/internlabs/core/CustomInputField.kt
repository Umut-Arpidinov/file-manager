package kg.o.internlabs.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CustomInputField : ConstraintLayout {
    lateinit var number_input_helper: TextView
    lateinit var cancel_image: ImageView
    constructor(context: Context) : super(context)


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        context.obtainStyledAttributes(attributeSet, R.styleable.CustomInputField).run {
            getText(R.styleable.CustomInputField_android_text)?.let {
                setHintText(it.toString())
            }

            getBoolean(R.styleable.CustomInputField_cancelImageVisibility,true)?.let {
                setCancelImageVisibility(it)
            }
            recycle()
        }

    }


    init {
        initView(context)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_input_field, this)
        number_input_helper = view.findViewById(R.id.number_input_helper_text)
        cancel_image = view.findViewById(R.id.number_input_cancel_image)


    }

    private fun setHintText(text: String) {
        number_input_helper.text = text
    }
    private fun setCancelImageVisibility(isVisible: Boolean){
        when(isVisible){
            true -> cancel_image.visibility = View.VISIBLE
            else -> cancel_image.visibility = View.GONE
        }
    }

}
