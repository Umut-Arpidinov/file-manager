package kg.o.internlabs.core.custom_views.number_input_view.custom_masked_edit_text

import android.content.Context
import android.text.Editable
import android.text.Selection
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import kg.o.internlabs.core.R
import kg.o.internlabs.core.custom_views.NumberInputHelper


class MaskEditText(context: Context, attr: AttributeSet?, mask: String, placeholder: Char) :
    AppCompatEditText(context, attr) {
    private var mask: String
    private var placeholder: String
    private var textWatcher: NumberInputHelper? = null
    private var fieldsNumber = 0

    constructor(context: Context, mask: String = "", placeholder: Char = ' ') : this(
        context,
        null,
        mask,
        placeholder
    )

    @JvmOverloads
    constructor(context: Context, attr: AttributeSet?, mask: String? = "") : this(
        context,
        attr,
        "",
        ' '
    )

    init {
        var mMask = mask
        var mPlaceholder = placeholder
        val a = context.obtainStyledAttributes(attr, R.styleable.MaskEditText)
        val n = a.indexCount
        for (i in 0 until n) {
            when (val at = a.getIndex(i)) {
                R.styleable.MaskEditText_masks -> mMask =
                    (mMask.ifEmpty { a.getString(at)!! })
                R.styleable.MaskEditText_placeholder -> mPlaceholder =
                    (if (a.getString(at)!!.isNotEmpty() && mPlaceholder == ' ') a.getString(at)!![0]
                    else mPlaceholder)
            }
        }
        a.recycle()
        this.mask = mMask
        this.placeholder = mPlaceholder.toString()
        addTextChangedListener(MaskTextWatcher())
        if (mMask.isNotEmpty()) text = text // sets the text to create the mask
    }

    fun setInterface(textWatcher: NumberInputHelper, fieldsNumber: Int) {
        this.textWatcher = textWatcher
        this.fieldsNumber = fieldsNumber
    }

    fun getValue(): String {
        return text.toString()
    }

    fun setValue(number: String) {
        this.setText(number)
    }

    fun eraseField() {
        this.setText(" ")
    }

    private fun formatMask(value: Editable) {
        val inputFilters = value.filters
        value.filters = arrayOfNulls(0)
        var i = 0
        var j = 0
        var maskLength = 0
        var treatNextCharAsLiteral = false
        val selection = Any()
        value.setSpan(
            selection,
            Selection.getSelectionStart(value),
            Selection.getSelectionEnd(value),
            Spanned.SPAN_MARK_MARK
        )
        while (i < mask.length) {
            if (!treatNextCharAsLiteral && isMaskChar(mask[i])) {
                if (j >= value.length) {
                    value.insert(j, placeholder)
                    value.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.gray_2, null)),
                        j,
                        j + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    j++
                } else if (!matchMask(mask[i], value[j])) {
                    value.delete(j, j + 1)
                    i--
                    maskLength--
                } else {
                    j++
                }
                maskLength++
            } else if (!treatNextCharAsLiteral && mask[i] == ESCAPE_CHAR) {
                treatNextCharAsLiteral = true
            } else {
                value.insert(j, mask[i].toString())
                value.setSpan(LiteralSpan(), j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                treatNextCharAsLiteral = false
                j++
                maskLength++
            }
            i++
        }
        while (value.length > maskLength) {
            val pos = value.length - 1
            value.delete(pos, pos + 1)
        }
        Selection.setSelection(value, value.getSpanStart(selection), value.getSpanEnd(selection))
        value.removeSpan(selection)
        value.filters = inputFilters
    }

    private fun stripMaskChars(value: Editable) {
        val pSpans = value.getSpans(0, value.length, PlaceholderSpan::class.java)
        val lSpans = value.getSpans(0, value.length, LiteralSpan::class.java)
        for (k in pSpans.indices) {
            value.delete(value.getSpanStart(pSpans[k]), value.getSpanEnd(pSpans[k]))
        }
        for (k in lSpans.indices) {
            value.delete(value.getSpanStart(lSpans[k]), value.getSpanEnd(lSpans[k]))
        }
    }

    private fun matchMask(mask: Char, value: Char): Boolean {
        return mask == NUMBER_MASK && Character.isDigit(value)
    }


    private fun isMaskChar(mask: Char): Boolean {
        when (mask) {
            NUMBER_MASK -> return true
        }
        return false
    }

    private inner class MaskTextWatcher : TextWatcher {
        private var updating = false
        override fun afterTextChanged(s: Editable) {
            if (updating || mask.isEmpty()) return
            if (!updating) {
                updating = true
                stripMaskChars(s)
                formatMask(s)
                updating = false
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            textWatcher?.numberWatcher(!s.endsWith('X'), fieldsNumber)

        }
    }

    private inner class PlaceholderSpan { // this class is used just to keep track of placeholders in the text
    }

    private inner class LiteralSpan { // this class is used just to keep track of literal chars in the text
    }

    companion object {
        private const val NUMBER_MASK = '#'
        private const val ESCAPE_CHAR = '\\'
    }
}