package uk.co.akm.util.barcode.lib.barcodeviewlib.attrs

import android.content.res.TypedArray

class CloseableTypedArrayImpl(override val array: TypedArray) : CloseableTypedArray {

    override fun close() {
        array.recycle()
    }
}