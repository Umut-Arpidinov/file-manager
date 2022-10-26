package kg.o.internlabs.core.custom_view

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

    private val adapter by lazy { RVAdapter() }

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


    private fun setNumber(number: Int) {
        val manager = GridLayoutManager(context, number)
        binding.rvOTP.layoutManager = manager
        binding.rvOTP.adapter = adapter
        val list = arrayListOf<OptEntity>()
        for (i in 0 until number) {
            list.add(OptEntity())
        }

        adapter.submitList(list)
    }


//    fun updateTv(text: String){
//        text.split("")
//        if (text.length == 4){
//
//        }
//    }
}

data class OptEntity(
    val text: String = "",
    var error: Boolean = false
)

class RVAdapter : ListAdapter<OptEntity, RVViewHolder>(otpDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val binding = ItemRecyclerCustomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RVViewHolder(private val binding: ItemRecyclerCustomBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: OptEntity?) {
        binding.one.text = item?.text
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
