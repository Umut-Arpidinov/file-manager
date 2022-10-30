package kg.o.internlabs.core.custom_number_input_view.custom_masked_edit_text

import kg.o.internlabs.core.R
import android.content.Context
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MaskedEditText : AppCompatEditText, TextWatcher {
    private var mask: String? = null
    private lateinit var rawToMask: IntArray
    private var rawText: RawText = RawText()
    private var editingBefore = false
    private var editingOnChanged = false
    private var editingAfter = false
    private lateinit var maskToRaw: IntArray
    private var initialized = false
    private var ignore = false
    private var maxRawLength = 0
    private var lastValidMaskPosition = 0
    private var selectionChanged = false
    private var place: Int = 0
    private var isKeepingText = false
    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MaskedEditText)
        mask = attributes.getString(R.styleable.MaskedEditText_mask)
        cleanUp()
        attributes.recycle()
    }

    private fun cleanUp() {
        initialized = false
        if (mask == null || mask!!.isEmpty()) {
            return
        }
        generatePositionArrays()
        if (!isKeepingText) {
            rawText = RawText()
            place = rawToMask[0]
        }
        editingBefore = true
        editingOnChanged = true
        editingAfter = true
        if (hasHint() && rawText.length() == 0) this.setText(makeMaskedTextWithHint())
        editingBefore = false
        editingOnChanged = false
        editingAfter = false
        maxRawLength = maskToRaw[previousValidPosition(mask!!.length - 1)] + 1
        lastValidMaskPosition = findLastValidMaskPosition()
        initialized = true

    }

    private fun findLastValidMaskPosition(): Int {
        for (i in maskToRaw.indices.reversed()) {
            if (maskToRaw[i] != -1) return i
        }
        throw RuntimeException("Mask must contain at least one representation char")
    }

    private fun hasHint() = hint != null

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
        init()
    }

    private fun generatePositionArrays() {
        val aux = IntArray(mask!!.length)
        maskToRaw = IntArray(mask!!.length)
        var charsInMaskAux = ""
        var charIndex = 0
        for (i in 0 until mask!!.length) {
            val currentChar = mask!![i]
            if (currentChar == '#') {
                aux[charIndex] = i
                maskToRaw[i] = charIndex++
            } else {
                val charAsString = currentChar.toString()
                if (!charsInMaskAux.contains(charAsString)) {
                    charsInMaskAux += charAsString
                }
                maskToRaw[i] = -1
            }
        }
        rawToMask = IntArray(charIndex)
        System.arraycopy(aux, 0, rawToMask, 0, charIndex)
    }

    private fun init() {
        addTextChangedListener(this)
    }

    override fun beforeTextChanged(
        s: CharSequence, start: Int, count: Int, after: Int
    ) {
        if (!editingBefore) {
            editingBefore = true
            if (start > lastValidMaskPosition) ignore = true
            var rangeStart = start
            if (after == 0) rangeStart = erasingStart(start)
            val range = calculateRange(rangeStart, start + count)
            if (range.start != -1) rawText.subtractFromString(range)
            if (count > 0) place = previousValidPosition(start)
        }
    }

    private fun erasingStart(begin: Int): Int {
        var start = begin
        while (start > 0 && maskToRaw[start] == -1) {
            start--
        }
        return start
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        var quantity = count
        if (!editingOnChanged && editingBefore) {
            editingOnChanged = true
            if (ignore) return
            if (quantity > 0) {
                val startingPosition = maskToRaw[nextValidPosition(start)]
                val addedString = s.subSequence(start, start + quantity).toString()
                quantity = rawText.addToString(addedString, startingPosition, maxRawLength)
                if (initialized) {
                    val currentPosition: Int =
                        if (startingPosition + quantity < rawToMask.size) rawToMask[startingPosition + quantity] else lastValidMaskPosition + 1
                    place = nextValidPosition(currentPosition)
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable) {
        if (!editingAfter && editingBefore && editingOnChanged) {
            editingAfter = true
            if (hasHint() || ( rawText.length() == 0)) setText(makeMaskedTextWithHint())
            selectionChanged = false
            setSelection(place)
            editingBefore = false
            editingOnChanged = false
            editingAfter = false
            ignore = false
        }
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        var begin = selStart
        var ends = selEnd
        if (initialized) {
            if (!selectionChanged) {
                begin = fixSelection(begin)
                ends = fixSelection(ends)

                if (begin > text!!.length) begin = text!!.length
                if (begin < 0) begin = 0

                if (ends > text!!.length) ends = text!!.length
                if (ends < 0) ends = 0
                setSelection(begin, ends)
                selectionChanged = true
            } else {
                if (begin > rawText.length() - 1) {
                    val start = fixSelection(begin)
                    val end = fixSelection(ends)
                    if (start >= 0 && end < text!!.length) setSelection(start, end)
                }
            }
        }
        super.onSelectionChanged(selStart, selEnd)
    }

    private fun fixSelection(selection: Int): Int {
        return if (selection > lastValidPosition()) lastValidPosition() else nextValidPosition(place)
    }

    private fun nextValidPosition(currentPlace: Int): Int {
        var currentPosition = currentPlace
        while (currentPosition < lastValidMaskPosition && maskToRaw[currentPosition] == -1) {
            currentPosition++
        }
        return if (currentPosition > lastValidMaskPosition) lastValidMaskPosition + 1 else currentPosition
    }

    private fun previousValidPosition(currentPlace: Int): Int {
        var currentPosition = currentPlace
        while (currentPosition >= 0 && maskToRaw[currentPosition] == -1) {
            currentPosition--
            if (currentPosition < 0) return nextValidPosition(0)


        }
        return currentPosition
    }

    private fun lastValidPosition(): Int {
        return if (rawText.length() == maxRawLength) rawToMask[rawText.length() - 1] + 1 else nextValidPosition(
            rawToMask[rawText.length()]
        )
    }

    private fun makeMaskedTextWithHint(): CharSequence {
        val ssb = SpannableStringBuilder()
        var mtrv: Int
        val maskFirstChunkEnd = rawToMask[0]
        for (i in 0 until mask!!.length) {
            mtrv = maskToRaw[i]
            if (mtrv != -1) {
                if (mtrv < rawText.length()) ssb.append(rawText.charAt(mtrv)) else ssb.append(hint[maskToRaw[i]])
            } else ssb.append(mask!![i])
            if (rawText.length() < rawToMask.size && i >= rawToMask[rawText.length()] && i >= maskFirstChunkEnd) {
                ssb.setSpan(ForegroundColorSpan(currentHintTextColor), i, i + 1, 0)
            }
        }
        return ssb
    }

    private fun calculateRange(start: Int, end: Int): Range {
        val range = Range()
        var i = start
        while (i <= end && i < mask!!.length) {
            if (maskToRaw[i] != -1) {
                if (range.start == -1) range.start = maskToRaw[i] else range.end = maskToRaw[i]

            }
            i++
        }
        if (end == mask!!.length) range.end = rawText.length()
        if (range.start == range.end && start < end) {
            val newStart = previousValidPosition(range.start - 1)
            range.start = newStart
        }
        return range
    }
}
