package kg.o.internlabs.core.custom_views

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.OtpInputCustomViewBinding
import kg.o.internlabs.core.databinding.OtpInputCustomViewBinding.inflate

class OtpInputCustomView : ConstraintLayout {

    companion object {
        var otpResend: OtpResend? = null
    }

    private val binding: OtpInputCustomViewBinding =
        inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, otpResend: OtpResend) : super(context) {
        OtpInputCustomView.otpResend = otpResend
        setTimer(0)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.OtpInputCustomView).run {
            getText(R.styleable.OtpInputCustomView_set_otp)?.let {
                setOtp(it.toString())
            }
            initWatcher()
            initClickers()
            recycle()
        }
    }

    fun setTimer(time: Long) {
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000)
                println("seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                println("done")
                //mTextField.setText("done!")
            }
        }.start()
    }

    private fun initClickers() = with(binding) {
        etOtp1.setOnClickListener {
            viewClicked(it.findViewById(R.id.et_otp1))
        }
        etOtp2.setOnClickListener {
            viewClicked(it.findViewById(R.id.et_otp2))
        }
        etOtp3.setOnClickListener {
            viewClicked(it.findViewById(R.id.et_otp3))
        }
        etOtp4.setOnClickListener {
            viewClicked(it.findViewById(R.id.et_otp4))
        }

        binding.tvResentButton.setOnClickListener {
            println(otpResend)
            otpResend?.sendOtpAgain()
        }
    }

    private fun viewClicked(it: EditText) {
        with(it){
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
    }

    private fun initWatcher() = with(binding) {
        etOtp1.addTextChangedListener {
            etOtp1.isFocusable = it.toString().isEmpty()
            with(etOtp2) {
                isFocusable = !etOtp1.isFocusable
                requestFocus()
            }
        }
        etOtp2.addTextChangedListener {
            etOtp2.isFocusable = it.toString().isEmpty()
            with(etOtp3) {
                isFocusable = !etOtp2.isFocusable
                requestFocus()
            }
        }
        etOtp3.addTextChangedListener {
            etOtp3.isFocusable = it.toString().isEmpty()
            with(etOtp4) {
                isFocusable = !etOtp2.isFocusable
                requestFocus()
            }
        }
        etOtp4.addTextChangedListener {
            etOtp4.isFocusable = it.toString().isEmpty()
        }
    }

    private fun setFirstValue(value: String) = with(binding) {
        etOtp1.setText(value)
        etOtp1.isFocusable = value.isEmpty()
        etOtp2.isFocusable = !etOtp1.isFocusable
    }

    private fun setSecondValue(value: String) = with(binding) {
        etOtp2.setText(value)
        etOtp2.isFocusable = value.isEmpty()
        etOtp3.isFocusable = !etOtp2.isFocusable
    }

    private fun setThirdValue(value: String) = with(binding) {
        etOtp3.setText(value)
        etOtp3.isFocusable = value.isEmpty()
        etOtp4.isFocusable = !etOtp3.isFocusable
    }

    private fun setFourthValue(value: String) = with(binding) {
        etOtp4.setText(value)
        etOtp4.isFocusable = value.isEmpty()
    }

    fun setOtp(values: String) {
        if (values.length > 4 || values.length < 4) return
        setFirstValue(values[0].toString())
        setSecondValue(values[1].toString())
        setThirdValue(values[2].toString())
        setFourthValue(values[3].toString())
    }

    fun setError(error: String) = with(binding) {
        with(tvResponseMessage) {
            text = error
            isVisible = true
        }
        tvResentButton.isVisible = true
        setErrorBackground()
    }

    private fun setErrorBackground() = with(binding) {
        changeBackgroundColor(this.fl1)
        changeBackgroundColor(this.fl2)
        changeBackgroundColor(this.fl3)
        changeBackgroundColor(this.fl4)
    }

    private fun changeBackgroundColor(fl: FrameLayout) {
        fl.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.bg_custom_view_error_sms, null)
    }

    fun getValues() = "${binding.etOtp1.text}${binding.etOtp2.text}" +
            "${binding.etOtp3.text}${binding.etOtp4.text}"
}

