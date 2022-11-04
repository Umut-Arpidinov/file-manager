package kg.o.internlabs.core.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.OtpInputCustomViewBinding
import kg.o.internlabs.core.databinding.OtpInputCustomViewBinding.inflate


class OtpInputCustomView : ConstraintLayout, View.OnClickListener {
    private var otpResend: OtpResend? = null

    private val binding: OtpInputCustomViewBinding =
        inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context ){
        println("-------------111 $otpResend")
    }
   /* constructor(context: Context, otpResend: OtpResend) : super(context){
        println("-------------------hhohohoh   ${this.otpResend}")
        this.otpResend = otpResend
        println("----------oooo---------hhohohoh   ${this.otpResend}")
    }*/
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        println("----------------222  $otpResend")
        context.obtainStyledAttributes(attrs, R.styleable.OtpInputCustomView).run {
            getText(R.styleable.OtpInputCustomView_set_otp)?.let {
                setOtp(it.toString())
            }
            recycle()
            initWatcher()
            initClickers()
        }
    }

    var touchType = -1 // No user touch yet.

    var onClickListener: () -> Unit = {
       println("on click not yet implemented")
        binding.tvResentButton.setOnClickListener {
            println("toched")
            otpResend?.sendOtpAgain()
        }
    }



    override fun onTouchEvent(e: MotionEvent?): Boolean {
        println(".................")
        val value = super.onTouchEvent(e)

        when(e?.action) {
            MotionEvent.ACTION_DOWN -> {
                println("toooooch")
                /* Determine where the user has touched on the screen. */
                touchType = 1 // for eg.
                return true
            }
            MotionEvent.ACTION_UP -> {
                println("toooooch")

                /* Now that user has lifted his finger. . . */
                when (touchType) {
                    1 -> {
                        println("toch type")
                        onClickListener()
                    }
                }
            }
            MotionEvent.ACTION_BUTTON_PRESS -> {

            }
        }
        return value
    }

    /*private var listener: OnClickListener? = null

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (listener != null) listener!!.onClick(this)
        }
        return super.dispatchTouchEvent(event)
    }


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.getAction() === KeyEvent.ACTION_UP && (event.getKeyCode() === KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() === KeyEvent.KEYCODE_ENTER)) {
            if (listener != null) listener!!.onClick(this)
        }
        return super.dispatchKeyEvent(event)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        this.listener = listener
    }
*/

    private fun initClickers() = with(binding) {
        etOtp1.setOnClickListener {
            with(etOtp1) {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
            }
        }
        etOtp2.setOnClickListener {
            with(etOtp2) {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
            }
        }
        etOtp3.setOnClickListener {
            with(etOtp3) {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
            }
        }
        etOtp4.setOnClickListener {
            with(etOtp4) {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
            }
        }

      /*  binding.tvResentButton.setOnClickListener {
            println(otpResend)
            otpResend?.sendOtpAgain()
        }*/
    }



    private fun initWatcher() = with(binding) {
        etOtp1.addTextChangedListener {
            etOtp1.isFocusable = it.toString().isEmpty()
            etOtp2.isFocusable = !etOtp1.isFocusable

        }
        etOtp2.addTextChangedListener {
            etOtp2.isFocusable = it.toString().isEmpty()
            etOtp3.isFocusable = !etOtp2.isFocusable
        }
        etOtp3.addTextChangedListener {
            etOtp3.isFocusable = it.toString().isEmpty()
            etOtp4.isFocusable = !etOtp3.isFocusable
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

    fun setError(error: String) = with(binding){
        with(tvResponseMessage){
            text = error
            isVisible = true
        }

        tvResentButton.isVisible = true
        setErrorBackground()
    }

    private fun setErrorBackground() = with(binding){
        fl1.background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_custom_view_error_sms, null)
        fl2.background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_custom_view_error_sms, null)
        fl3.background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_custom_view_error_sms, null)
        fl4.background = ResourcesCompat.getDrawable(resources,
            R.drawable.bg_custom_view_error_sms, null)
    }

    fun getValues() = "${binding.etOtp1.text}${binding.etOtp2.text}" +
            "${binding.etOtp3.text}${binding.etOtp4.text}"

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}

