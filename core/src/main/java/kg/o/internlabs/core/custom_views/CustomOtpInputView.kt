package kg.o.internlabs.core.custom_views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CustomOtpInputViewBinding

class CustomOtpInputView : ConstraintLayout {
    private var errorMessage = ""
    private var otpHelper: OtpHelper? = null
    private var hasFirstValue = false
    private var hasSecondValue = false
    private var hasThirdValue = false
    private var hasFourthValue = false

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

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun initClickers() = with(binding) {
        clicked(etOtp1)
        clicked(etOtp2)
        clicked(etOtp3)
        clicked(etOtp4)

        tvResentButton.setOnClickListener {
            otpHelper?.sendOtpAgain()
        }
    }

    private fun clicked(et: EditText) {
        et.setOnClickListener {
            with(it) {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
            }
        }
    }

    fun setInterface(otpHelper: OtpHelper) {
        this.otpHelper = otpHelper
    }

    private fun initWatcher() = with(binding) {
        watch(etOtp1, etOtp2, etOtp1, 1)
        watch(etOtp2, etOtp3, etOtp1, 2)
        watch(etOtp3, etOtp4, etOtp2, 3)
        watch(etOtp4, etOtp3, etOtp3, 4)
    }

    private fun watch(et1: EditText, et2: EditText, et: EditText, cellsPosition: Int) {
        var before = false
        var after: Boolean
        et1.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                before = s.isNullOrEmpty()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                after = s.isNullOrEmpty()
                println("***** $after   *** $before")
                if (before) {
                    if (cellsPosition == 4) {
                        watch(et1)
                    } else {
                        normalFocus(et1, et2, cellsPosition)
                    }
                } else {
                    abnormalFocus(et1, et2, et, cellsPosition)
                }
            }
        })
    }

    private fun watch(et: EditText) {
        et.addTextChangedListener {
            et.isFocusable = it.toString().isEmpty()
            hasFourthValue = !et.isFocusable
            watcher()
        }
    }

    private fun normalFocus(et1: EditText, et2: EditText, cellsPosition: Int) {
        println("normal")
        et1.isFocusable = false
        with(et2) {
            isFocusable = !et1.isFocusable
            requestFocus()
            when (cellsPosition) {
                1 -> hasFirstValue = isFocusable
                2 -> hasSecondValue = isFocusable
                3 -> hasThirdValue = isFocusable
            }
            watcher()
        }
    }

    private fun abnormalFocus(et1: EditText, et2: EditText, et: EditText, cellsPosition: Int) {
        println("abnormal  $cellsPosition  $et1    $et2")
        if (cellsPosition != 1) {
            if (cellsPosition == 4) {
                et1.isFocusable = false
                with(et2) {
                    isFocusable = !et1.isFocusable
                    requestFocus()
                    hasThirdValue = isFocusable
                }
            } else {
                et1.isFocusable = false
                with(et) {
                    isFocusable = !et1.isFocusable
                    requestFocus()
                    when (cellsPosition) {
                        2 -> hasFirstValue = isFocusable
                        3 -> hasSecondValue = isFocusable
                    }
                }
            }
            watcher()
        } else {
            watch(et1)
        }
    }

    private fun watcher() {
        otpHelper?.watcher(hasFirstValue && hasSecondValue && hasThirdValue && hasFourthValue)
        errorWatcher()
    }

 private fun errorWatcher() {
        if(!hasFirstValue || !hasSecondValue || !hasThirdValue || !hasFourthValue){
            setError(errorMessage)
        }
    }
    fun setOtp(values: String) = with(binding) {
        if (values.length > 4 || values.length < 4) return
        etOtp1.setText(values[0].toString())
        etOtp2.setText(values[1].toString())
        etOtp3.setText(values[2].toString())
        etOtp4.setText(values[3].toString())
    }

    fun setError(error: String = "") = with(binding) {
        errorMessage = error
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
