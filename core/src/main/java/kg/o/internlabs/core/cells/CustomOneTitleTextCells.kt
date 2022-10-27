package kg.o.internlabs.core.cells

import android.content.Context
import android.util.AttributeSet
import android.view.View
import kg.o.internlabs.core.R

class CustomOneTitleTextCells : View {
    private var position= "single"
    private var hasIcon = false
    private var hasDetails = false
    private var hasShevron = false


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.OtpInputCustomView).run {
            getText(R.styleable.OtpInputCustomView_otp_custom_view)?.let {
                setNumber(it.toString().toInt())
            }
            recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}