package kg.o.internlabs.core.custom_views.cells

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kg.o.internlabs.core.R
import kg.o.internlabs.core.custom_views.cells.cells_utils.CustomOneCellsClickListener
import kg.o.internlabs.core.databinding.CustomOneCellViewBinding


class CustomOneCellView : ConstraintLayout {
    private var posOfCell: Int = 0
    private var cellListener: CustomOneCellsClickListener? = null

    private val binding = CustomOneCellViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomOneCellView).run {

            isViewEditable(getBoolean(R.styleable.CustomOneCellView_isEditableView, false))

            getString(R.styleable.CustomOneCellView_setHint)?.let {
                setHint(it)
            }

            getString(R.styleable.CustomOneCellView_setText)?.let {
                setText(it)
            }

            inputType(getInteger(R.styleable.CustomOneCellView_android_inputType, 0))

            setDelimiter(getInteger(R.styleable.CustomOneCellView_setDelimiter, 0))

            initListeners()
            recycle()
        }
    }

    fun inputType(type: Int) {
        binding.etEditable.inputType = type
    }

    fun setDelimiter(delimiter: Int) = with(binding) {
        if (delimiter == 0) return
        tilEditable.counterMaxLength = delimiter
        tilEditable.isCounterEnabled = false

        etEditable.filters = arrayOf<InputFilter>(
            LengthFilter(delimiter)
        )
    }


    fun isViewEditable(isViewEditable: Boolean) = with(binding) {
        flEditable.isVisible = isViewEditable
        flNotEditable.isVisible = isViewEditable.not()
    }

    fun setHint(hint: String) = with(binding) {
        if (flEditable.isVisible) {
            etEditable.hint = hint
            return
        }
        tvHint.text = hint
    }

    fun setText(text: String) = with(binding) {
        if (flEditable.isVisible) {
            etEditable.setText(text)
            return
        }
        tvHint.isVisible = false
        tvTitle.text = text
        tvTitle.isVisible = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() = with(binding) {
        etEditable.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    etEditable.isFocusable = true
                    etEditable.isFocusableInTouchMode = true
                }
            }

            v?.onTouchEvent(event) ?: true
        }

        etEditable.setOnFocusChangeListener { _, hasFocus ->
            run {
                tilEditable.isCounterEnabled = hasFocus
            }
        }

        etEditable.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                var converted: String = s.toString()
                if (converted.contains("\n")) {
                    converted = converted.replace("\n", "")
                    etEditable.setText(converted)
                    etEditable.text?.let { etEditable.setSelection(it.length) }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        ivShevron.setOnClickListener {
            cellListener?.cellClicked(getHint(), posOfCell)
        }

        otherViewsTouchListener(flNotEditable)
        otherViewsTouchListener(tvHint)
        otherViewsTouchListener(tvTitle)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun otherViewsTouchListener(view: View) {
        view.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.isFocusable = true
                    view.requestFocus()
                    view.isFocusableInTouchMode = true
                }
            }

            v?.onTouchEvent(event) ?: true
        }
    }

    fun getHint(): String {
        with(binding) {
            if (flEditable.isVisible) return etEditable.hint.toString()
            return tvHint.text.toString()
        }
    }

    fun getText(): String {
        with(binding) {
            if (flEditable.isVisible) return etEditable.text.toString()
            return tvTitle.text.toString()
        }
    }

    fun setInterface(cellsClickListener: CustomOneCellsClickListener, posOfCell: Int = 0) {
        cellListener = cellsClickListener
        this.posOfCell = posOfCell
    }
}