package kg.o.internlabs.core.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CustomPasswordInputFieldViewBinding
import kg.o.internlabs.core.databinding.CustomPasswordInputFieldViewBinding.inflate

class PasswordInputFieldView : ConstraintLayout{
    private val binding: CustomPasswordInputFieldViewBinding= inflate(LayoutInflater.from(context),this,true)
    constructor(context: Context) : super(context)
    constructor(context: Context,attrs: AttributeSet?) : super(context,attrs){
        context.obtainStyledAttributes(attrs, R.styleable.PasswordInputFieldView).run{
            getText(R.styleable.NumberInputView_helperTextState)?.let {

            }

        }
    }
    private fun setHintText(state: String) = with(binding){
        passwordHelper.text = state
    }
}