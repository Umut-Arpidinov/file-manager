package kg.o.internlabs.core.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
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
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.OtpInputCustomView).run {
            getText(R.styleable.OtpInputCustomView_set_first_value)?.let {
                setFirstValue(it.toString())
            }
            getText(R.styleable.OtpInputCustomView_set_second_value)?.let {
                setSecondValue(it.toString())
            }
            getText(R.styleable.OtpInputCustomView_set_third_value)?.let {
                setThirdValue(it.toString())
            }
            getText(R.styleable.OtpInputCustomView_set_fourth_value)?.let {
                setFourthValue(it.toString())
            }
            recycle()
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


}
