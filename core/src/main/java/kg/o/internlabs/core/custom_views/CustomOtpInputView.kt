package kg.o.internlabs.core.custom_views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CustomOtpInputViewBinding


class CustomOtpInputView : ConstraintLayout {
    private var otpHelper: OtpHelper? = null

    private val binding = CustomOtpInputViewBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomOtpInputView).run {
            getText(R.styleable.CustomOtpInputView_set_otp)?.let {
                setOtp(it.toString())
            }
            initWatcher()
            initClickers()
            recycle()
        }
    }

    private fun initClickers() = with(binding) {
        clicked(etOtp1, 1)
        clicked(etOtp2, 2)
        clicked(etOtp3, 3)
        clicked(etOtp4, 4)

        tvResentButton.setOnClickListener {
            otpHelper?.sendOtpAgain()
        }
    }

    private fun clicked(et: EditText, i: Int) {
        et.setOnClickListener {
            println("---==------==----")
            with(it) {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
            }
        }
        //initWatcher()
    }

    fun setInterface(otpHelper: OtpHelper) {
        this.otpHelper = otpHelper
    }

    private fun initWatcher() = with(binding) {
        watch(etOtp1, 1)
        watch(etOtp2, 2)
        watch(etOtp3, 3)
        watch(etOtp4, 4)

    }

    private fun watch(et1: EditText, cellsPosition: Int) {
        et1.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (before > count) {
                    deleting(cellsPosition)
                } else {
                    adding(cellsPosition)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                watcher()
            }
        })
    }

    private fun adding(cellsPosition: Int) = with(binding){
        println("adding  $cellsPosition")
        when(cellsPosition) {
            1 -> {
                etOtp1.isFocusable = false
                etOtp2.isFocusable = true
                etOtp2.requestFocus()
            }
            2 -> {
                etOtp2.isFocusable = false
                etOtp3.isFocusable = true
                etOtp3.requestFocus()
            }
            3 -> {
                etOtp3.isFocusable = false
                etOtp4.isFocusable = true
                etOtp4.requestFocus()
            }

            else -> {
                etOtp1.isFocusable = true
            }
        }
    }

    private fun deleting(cellsPosition: Int) = with(binding){
        println("deleting   $cellsPosition")
        when(cellsPosition) {
            2 -> {
               etOtp2.isFocusable = false
                etOtp2.clearFocus()
                etOtp1.isFocusable = true
                etOtp1.requestFocus()
                val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.showSoftInput(etOtp1, InputMethodManager.SHOW_IMPLICIT)
            }
            3 -> {
               etOtp3.isFocusable = false
                etOtp3.clearFocus()
                etOtp2.isFocusable = true
                etOtp2.requestFocus()
                val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.showSoftInput(etOtp2, InputMethodManager.SHOW_IMPLICIT)
            }
            4 -> {
               etOtp4.isFocusable = false
                etOtp4.clearFocus()
                etOtp3.isFocusable = true
                etOtp3.requestFocus()
                val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.showSoftInput(etOtp3, InputMethodManager.SHOW_IMPLICIT)
            }

            else -> {
                etOtp1.isFocusable = true
            }
        }
    }

    private fun watcher() {
        println("length     ${getValues().length}")
        if (getValues().length != 4) {
            setError()
            otpHelper?.watcher(false)
            return
        }
        println("length2     ${getValues().length}")

        otpHelper?.watcher(true)
    }

    fun setOtp(values: String) = with(binding) {
        if (values.length > 4 || values.length < 4) return
        etOtp1.setText(values[0].toString())
        etOtp2.setText(values[1].toString())
        etOtp3.setText(values[2].toString())
        etOtp4.setText(values[3].toString())
    }

    fun setError(error: String = "") = with(binding) {
        with(tvResponseMessage) {
            text = error
            isVisible = error.isNotEmpty()
        }
        tvResentButton.isVisible = tvResponseMessage.isVisible
        setErrorBackground(error.isNotEmpty())
    }

    private fun setErrorBackground(notEmpty: Boolean) = with(binding) {
        changeBackgroundColor(fl1, notEmpty)
        changeBackgroundColor(fl2, notEmpty)
        changeBackgroundColor(fl3, notEmpty)
        changeBackgroundColor(fl4, notEmpty)
    }

    private fun changeBackgroundColor(fl: FrameLayout, notEmpty: Boolean) {
        if (notEmpty) {
            fl.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_custom_view_error_sms, null
            )
            return
        }
        fl.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.bg_custom_view_sms, null
        )
    }

    fun getValues() = with(binding) {
        "${etOtp1.text}${etOtp2.text}" +
                "${etOtp3.text}${etOtp4.text}"
    }
}
