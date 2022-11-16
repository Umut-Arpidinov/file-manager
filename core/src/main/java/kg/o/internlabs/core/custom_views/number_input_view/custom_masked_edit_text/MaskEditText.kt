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
    private var fieldValues = ""

    constructor(context: Context, mask: String = "", placeholder: Char = ' ') : this(
        context,
        null,
        mask,
        placeholder
    ) {
    }

    @JvmOverloads
    constructor(context: Context, attr: AttributeSet?, mask: String? = "") : this(
        context,
        attr,
        "",
        ' '
    ) {
    }

    init {
        var mask = mask
        var placeholder = placeholder
        val a = context.obtainStyledAttributes(attr, R.styleable.MaskEditText)
        val N = a.indexCount
        for (i in 0 until N) {
            val at = a.getIndex(i)
            when (at) {
                R.styleable.MaskEditText_masks -> mask =
                    (if (mask.length > 0) mask else a.getString(at)!!)
                R.styleable.MaskEditText_placeholder -> placeholder =
                    (if (a.getString(at)!!.length > 0 && placeholder == ' ') a.getString(at)!![0] else placeholder)
            }
        }
        a.recycle()
        this.mask = mask
        this.placeholder = placeholder.toString()
        addTextChangedListener(MaskTextWatcher())
        if (mask.length > 0) text = text // sets the text to create the mask
    }
    fun setInterface(textWatcher: NumberInputHelper, fieldsNumber: Int) {
        this.textWatcher = textWatcher
        this.fieldsNumber = fieldsNumber
    }
    fun getValues() = fieldValues


    fun getValue(): String{
        return text.toString()
    }

    fun eraseField(){
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
                    value.setSpan(ForegroundColorSpan(resources.getColor(R.color.gray_2)), j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
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
        val pspans = value.getSpans(0, value.length, PlaceholderSpan::class.java)
        val lspans = value.getSpans(0, value.length, LiteralSpan::class.java)
        for (k in pspans.indices) {
            value.delete(value.getSpanStart(pspans[k]), value.getSpanEnd(pspans[k]))
        }
        for (k in lspans.indices) {
            value.delete(value.getSpanStart(lspans[k]), value.getSpanEnd(lspans[k]))
        }
    }

    private fun matchMask(mask: Char, value: Char): Boolean {
        var ret = mask == NUMBER_MASK && Character.isDigit(value)
        return ret
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
            if (updating || mask.length == 0) return
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
            textWatcher?.numberWatcher(s[s.length-1] != 'X', fieldsNumber)
            fieldValues = s.toString()

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