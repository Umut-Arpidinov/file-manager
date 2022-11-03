package kg.o.internlabs.core.custom_views

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.ItemRecyclerCustomBinding
import kg.o.internlabs.core.databinding.OtpInputCustomViewBinding
import kg.o.internlabs.core.databinding.OtpInputCustomViewBinding.inflate


class OtpInputCustomView : ConstraintLayout {

    private val binding: OtpInputCustomViewBinding =
        inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    //constructor(context: Context, otpResend: OtpResend) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.OtpInputCustomView).run {
            getText(R.styleable.OtpInputCustomView_set_otp)?.let {
                setOtp(it.toString())
            }
            recycle()
            initWatcher()
            initClickers()
        }
    }

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

        //binding.tvResentButton.setOnClickListener()
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
    }

    fun getValues() = "${binding.etOtp1.text.toString()}${binding.etOtp2.text.toString()}" +
            "${binding.etOtp3.text.toString()}${binding.etOtp4.text.toString()}"
}

