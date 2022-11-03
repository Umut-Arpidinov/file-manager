package kg.o.internlabs.core.custom_views

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.PasswordInputViewBinding
import kg.o.internlabs.core.databinding.PasswordInputViewBinding.inflate

class CustomPasswordInputFieldView : ConstraintLayout {
    private val binding: PasswordInputViewBinding =
        inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomPasswordInputFieldView).run {
            getText(R.styleable.CustomPasswordInputFieldView_setMessage)?.let {
                setMessage(it.toString(),false)
            }
            getText(R.styleable.CustomPasswordInputFieldView_setPasswordHint)?.let {
                setPasswordHint(it.toString())
            }
            recycle()
            initClick()
        }
    }

    fun setMessage(message: String, isError: Boolean) = with(binding){
        if(isError) setErrorMessage(message)
        else passwordHelper.text  = message
    }

    fun setErrorMessage(message: String) = with(binding){
        passwordHelper.text = message
        setFrameErrorColor()
        setTextErrorColor()
    }


    fun setPasswordHint(text: String) = with(binding) {
        passwordInputField.hint = text
    }

    private fun setFrameErrorColor() = with(binding) {
        frame.background = ResourcesCompat.getDrawable(
            resources, R.drawable.number_not_ok_style, null
        )
    }

    private fun setTextErrorColor() = with(binding) {
        passwordHelper.setTextColor(ContextCompat.getColor(context, R.color.red_1))
    }

    private fun initClick() = with(binding) {
        passwordToggle.setOnClickListener {
            if (passwordInputField.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                passwordInputField.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                passwordToggle.setImageResource(R.drawable.ic_outline_visibility_off_24)
            } else {
                passwordInputField.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                passwordToggle.setImageResource(R.drawable.ic_outline_visibility_24)
            }
        }
    }
    fun getPasswordField(): String = binding.passwordInputField.text.toString()

}