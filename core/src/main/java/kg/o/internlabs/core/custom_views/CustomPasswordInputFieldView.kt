package kg.o.internlabs.core.custom_views

import android.content.Context
import android.text.Html
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CustomPasswordInputViewBinding

class CustomPasswordInputFieldView : ConstraintLayout {

    private var textWatcher: PasswordInputHelper? = null
    private var fieldNumber = 0

    private val binding = CustomPasswordInputViewBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomPasswordInputFieldView).run {
            getText(R.styleable.CustomPasswordInputFieldView_setMessage)?.let {
                setMessage(it.toString())
            }
            getText(R.styleable.CustomPasswordInputFieldView_setPasswordHint)?.let {
                setPasswordHint(it.toString())
            }
            recycle()
            initClick()
            initWatcher()
        }
    }

    private fun initWatcher() {
        binding.passwordInputField.addTextChangedListener {
            textWatcher?.passwordWatcher(isPasswordStrong(it.toString()), fieldNumber)
        }

    }

    fun setInterface(textWatcher: PasswordInputHelper, fieldNumber: Int = 0) {
        this.textWatcher = textWatcher
        this.fieldNumber = fieldNumber
    }

    fun setMessage(message: String) = with(binding) {
        passwordHelper.text = Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY)
        setFrameDefaultColor()
        setTextDefaultColor()
    }

    fun setErrorMessage(message: String) = with(binding) {
        passwordHelper.text = Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY)
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

    private fun setFrameDefaultColor() = with(binding) {
        frame.background = ResourcesCompat.getDrawable(
            resources, R.drawable.number_ok_style, null
        )
    }

    private fun setTextErrorColor() = with(binding) {
        passwordHelper.setTextColor(ContextCompat.getColor(context, R.color.red_1))
    }

    private fun setPasswordInfo(password: String) {
        when (password) {
            "short" -> setMessage(resources.getString(R.string.helper_text_wrong_password_length))
            "noUpperCaseLetter" -> setMessage(resources.getString(R.string.helper_text_no_upper_case_letters))
            "noLowerCaseLetter" -> setMessage(resources.getString(R.string.helper_text_no_lower_case_letters))
            "emptySpace" -> setMessage(resources.getString(R.string.helper_text_space))
            "noDigits" -> setMessage(resources.getString(R.string.helper_text_no_digits))
            "ok" -> setMessage(resources.getString(R.string.helper_text_create_password))
        }
    }

    private fun isPasswordStrong(password: String): Boolean {
        if (password.length < 8) {
            setPasswordInfo("short")
            return false
        }

        if (password.matches(".*[A-Z].*".toRegex()).not()) {
            setPasswordInfo("noUpperCaseLetter")
            return false
        }
        if (password.matches(".*[a-z].*".toRegex()).not()) {
            setPasswordInfo("noLowerCaseLetter")
            return false
        }
        if (password.contains(" ")) {
            setPasswordInfo("emptySpace")
            return false
        }
        if (password.matches(".*[0-9].*".toRegex()).not()) {
            setPasswordInfo("noDigits")
            return false
        }

        setPasswordInfo("ok")
        return true
    }

    private fun setTextDefaultColor() = with(binding) {
        passwordHelper.setTextColor(ContextCompat.getColor(context, R.color.black_1))
        passwordHelper.setTextAppearance(R.style.hint)
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
            passwordInputField.setSelection(passwordInputField.text.length)
        }
    }

    fun getValueFromPasswordField() = binding.passwordInputField.text.toString()
    fun setValueToPasswordField(message: String) {
        binding.passwordInputField.setText(message)
    }
}