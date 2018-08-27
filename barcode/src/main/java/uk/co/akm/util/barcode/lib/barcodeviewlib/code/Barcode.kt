package uk.co.akm.util.barcode.lib.barcodeviewlib.code

/**
 * Created by Thanos Mavroidis on 27/08/2018.
 */
interface Barcode {

    fun textSupported(text: String): Boolean

    fun fitsInWidth(text: String, width: Int): Boolean

    fun numberOfModules(text: String): Int

    fun generateBarcode(text: String, width: Int): Array<Pair<Int, Int>>
}