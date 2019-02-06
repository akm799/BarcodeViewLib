package uk.co.akm.util.barcode.lib.barcodeviewlib.attrs

import android.content.res.Resources
import android.util.AttributeSet

fun Resources.Theme.obtainCloseableStyledAttributes(set: AttributeSet?, attrs: IntArray, defStyleAttr: Int): CloseableTypedArray {
    val array = obtainStyledAttributes(set, attrs, defStyleAttr, 0)

    return CloseableTypedArrayImpl(array)
}