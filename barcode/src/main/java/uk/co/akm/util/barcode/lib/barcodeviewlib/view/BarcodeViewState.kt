package uk.co.akm.util.barcode.lib.barcodeviewlib.view

import android.os.Parcel
import android.os.Parcelable
import android.view.View

class BarcodeViewState : View.BaseSavedState {
    companion object {
        val CREATOR = object : Parcelable.Creator<BarcodeViewState> {
            override fun createFromParcel(parcel: Parcel?): BarcodeViewState = BarcodeViewState(parcel)

            override fun newArray(size: Int): Array<BarcodeViewState?> = arrayOfNulls(size)
        }
    }

    private var textValue: String
    private var barColourValue: Int
    private var moduleWidthPxValue: Int
    private var widthOverHeightRatioValue: Float

    val text: String
        get() = textValue

    val barColour: Int
        get() = barColourValue

    val moduleWidthPx: Int
        get() = moduleWidthPxValue

    val widthOverHeightRatio: Float
        get() = widthOverHeightRatioValue

    constructor(superState: Parcelable, text: String, barColour: Int, moduleWidthPx: Int, widthOverHeightRatio: Float) : super(superState) {
        this.textValue = text
        this.barColourValue = barColour
        this.moduleWidthPxValue = moduleWidthPx
        this.widthOverHeightRatioValue = widthOverHeightRatio
    }

    private constructor(parcel: Parcel?) : super(parcel) {
        textValue = parcel?.readString() ?: "" // Parcel argument should never be null.
        barColourValue = parcel?.readInt() ?: 0
        moduleWidthPxValue = parcel?.readInt() ?: 0
        widthOverHeightRatioValue = parcel?.readFloat() ?: 0f
    }

    override fun writeToParcel(out: Parcel?, flags: Int) {
        super.writeToParcel(out, flags)
        out?.writeString(textValue)
        out?.writeInt(barColourValue)
        out?.writeInt(moduleWidthPxValue)
        out?.writeFloat(widthOverHeightRatioValue)
    }
}