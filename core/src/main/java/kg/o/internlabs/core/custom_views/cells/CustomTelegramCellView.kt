package kg.o.internlabs.core.custom_views.cells

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CustomTelegramCellViewBinding


class CustomTelegramCellView : ConstraintLayout {

    private val binding = CustomTelegramCellViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomTelegramCellView).run {

            getString(R.styleable.CustomTelegramCellView_android_text)?.let {
                setText(it)
            }

            initListeners()
            recycle()
        }
    }

    fun setText(text: String) = with(binding) {
        etTelegramNick.setText(text)
        etTelegramNick.setSelection(etTelegramNick.length())
    }

    private fun initListeners() = with(binding) {

        numberInputCancelImage.setOnClickListener {
            println("===1==="+getText())
            if (getText().length > 1) {
                println("===2==="+getText().length)
                etTelegramNick.setText(getText().dropLast(getText().length - 1))
                println("===3==="+getText().length)
                println("===4==="+getText())
            }
        }

        etTelegramNick.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 1) {
                    println("------1----$s")
                    if (isAllowedChar(s[s.length - 1]).not() || (s.length > 33)) {
                        etTelegramNick.setText(s.dropLast(1))
                    }
                }
                etTelegramNick.text?.let { etTelegramNick.setSelection(it.length) }
            }

            override fun afterTextChanged(s: Editable?) {
                numberInputCancelImage.isVisible = getText().length > 1
            }
        })


    }

    private fun isAllowedChar(char: Char): Boolean {
        when (char.code) {
            in 48..57, in 65..90, 95, in 97..122 -> return true
        }
        return false
    }

    fun getText() = binding.etTelegramNick.text.toString()
}