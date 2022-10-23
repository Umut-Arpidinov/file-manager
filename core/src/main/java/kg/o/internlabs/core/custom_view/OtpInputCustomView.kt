package kg.o.internlabs.core.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.OtpInputCustomViewBinding
import kg.o.internlabs.core.databinding.OtpInputCustomViewBinding.inflate


class OtpInputCustomView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr){
        context.obtainStyledAttributes(attrs, R.styleable.OtpInputCustomView).run {
            getText(R.styleable.OtpInputCustomView_otp_custom_view)?.let {
               otp(it.toString().toInt())
            }
            recycle()
    }

    }

    private val binding: OtpInputCustomViewBinding =
        inflate(LayoutInflater.from(context), this, true)

    private val adapter by lazy { RVAdapter() }


    init {

    }

    fun updateTv(text: String) {
        text.split("")
        if (text.length == 6) {

        }
    }

    fun otp(int: Int) {
        val manager = GridLayoutManager(context, 6)
        binding.rvOTP.layoutManager = manager
        binding.rvOTP.adapter = adapter
        val list = arrayListOf<OptEntity>()
        for (i in 0..int) {
            list.add(OptEntity("", false))
        }

//        list.map { it.error = true }

        adapter.submitList(list)
    }


}

data class OptEntity(
    val text: String,
    var error: Boolean
)

class RVAdapter : ListAdapter<OptEntity, RVViewHolder>(otpDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        return RVViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RVViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private var tvTitle: TextView? = null

    init {
        tvTitle = view.findViewById(R.id.one)
    }

    fun bind(item: OptEntity?) {
        tvTitle?.text = item?.text
    }

    companion object {
        fun create(parent: ViewGroup): RVViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_custom, parent, false)
            return RVViewHolder(view)
        }
    }

}

val otpDiff = object : DiffUtil.ItemCallback<OptEntity>() {
    override fun areItemsTheSame(oldItem: OptEntity, newItem: OptEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: OptEntity, newItem: OptEntity): Boolean {
        return oldItem.error == newItem.error && oldItem.text == newItem.text
    }

}