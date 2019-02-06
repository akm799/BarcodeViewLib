package uk.co.akm.util.barcode.lib.barcodeviewlib.attrs

import android.content.res.TypedArray
import java.io.Closeable

/**
 * Wrapper around a [TypedArray] that also implements the [Closeable] interface.
 */
interface CloseableTypedArray : Closeable {

    val array : TypedArray
}