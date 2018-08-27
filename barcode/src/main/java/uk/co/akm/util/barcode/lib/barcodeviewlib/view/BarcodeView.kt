package uk.co.akm.util.barcode.lib.barcodeviewlib.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import uk.co.akm.util.barcode.lib.barcodeviewlib.R
import uk.co.akm.util.barcode.lib.barcodeviewlib.code.Code128bBarcode

class BarcodeView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : View(context, attrs, defStyleAttr, defStyleRes) {
    companion object {
        const val TAG = "BarcodeView"
    }

    private val barPaint = Paint()
    private val barcode = Code128bBarcode()

    private val defaultModuleWidthDp = 2
    private val defaultWidthOverHeightRatio = 6f
    private val defaultBarColour = Color.BLACK

    var text: String?
        get() = textValue
        set(value) = setTextValue(value)

    private var textValue: String? = null
    private var barColourValue = defaultBarColour
    private var moduleWidthPxValue = 0
    private var widthOverHeightRatioValue = defaultWidthOverHeightRatio

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BarcodeView, defStyleAttr, defStyleRes).apply {
            try {
                textValue = getString(R.styleable.BarcodeView_text)
                barColourValue = getColor(R.styleable.BarcodeView_barColour, defaultBarColour)
                moduleWidthPxValue = Math.round(getDimension(R.styleable.BarcodeView_moduleWidth, 0f))
                widthOverHeightRatioValue = getFloat(R.styleable.BarcodeView_widthOverHeight, defaultWidthOverHeightRatio)
            } finally {
                recycle()
            }
        }

        isSaveEnabled = true
        correctDimensionValues()
        barPaint.color = barColourValue
    }

    private fun correctDimensionValues() {
        if (moduleWidthPxValue == 0) {
            moduleWidthPxValue = dpToPx(defaultModuleWidthDp)
        }

        if (widthOverHeightRatioValue <= 0f) {
            widthOverHeightRatioValue = defaultWidthOverHeightRatio
        }
    }

    private fun dpToPx(dp: Int): Int {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)

        return Math.round(px)
    }

    private fun setTextValue(text: String?) {
        textValue = if (textIsSuitable(text)) text else null
        requestLayout()
    }

    private fun textIsSuitable(text: String?): Boolean {
        if (text == null || text.isEmpty()) {
            return false
        } else if (!barcode.textSupported(text)) {
            Log.e(TAG, "Barcode text input '$text' contains at least one character which does not belong to the Code 128 B set. This barcode view only supports Code 128 B characters.")
            return false
        } else if (width > 0 && !barcode.fitsInWidth(text, width)) { // If the width is zero it means (probably) that it is has not been calculated, so there is no point to see if the barcode will fit.
            Log.e(TAG, "Barcode text input '$text' will produce a barcode that is too wide to fit within the available width of $width.")
            return false
        } else {
            return true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = computeDesiredWidth()
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(desiredWidth, widthSize)
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> desiredWidth
        }

        val desiredHeight = Math.round(desiredWidth/widthOverHeightRatioValue)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> Math.min(desiredHeight, heightSize)
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    private fun computeDesiredWidth(): Int = text?.let { barcode.numberOfModules(it)*moduleWidthPxValue } ?: 0

    override fun onSaveInstanceState(): Parcelable = stateToSave(super.onSaveInstanceState(), textValue)

    private fun stateToSave(superState: Parcelable, textValue: String?): Parcelable {
        return if (textValue == null) superState else BarcodeViewState(superState, textValue, barColourValue, moduleWidthPxValue, widthOverHeightRatioValue)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is BarcodeViewState) {
            super.onRestoreInstanceState(state.superState)
            textValue = state.text
            barColourValue = state.barColour
            moduleWidthPxValue = state.moduleWidthPx
            widthOverHeightRatioValue = state.widthOverHeightRatio
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null && textIsSuitable(textValue)) {
            textValue?.let { drawBarcode(canvas, it) }
        }
    }

    private fun drawBarcode(canvas: Canvas, text: String) {
        val pattern = barcode.generateBarcode(text, width)
        pattern.forEach {
            canvas.drawRect(it.first.toFloat(), 0f, it.second.toFloat(), height.toFloat(), barPaint)
        }
    }
}