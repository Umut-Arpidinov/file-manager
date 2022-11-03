package kg.o.internlabs.core.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
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
            getText(R.styleable.OtpInputCustomView_otp_custom_view)?.let {
                setValues(it.toString().toInt())
            }
            recycle()
        }
    }

}
