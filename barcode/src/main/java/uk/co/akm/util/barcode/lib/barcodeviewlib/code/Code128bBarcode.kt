package uk.co.akm.util.barcode.lib.barcodeviewlib.code

import uk.co.akm.util.barcode.lib.barcodeviewlib.code.data.Code128BData
import uk.co.akm.util.barcode.lib.barcodeviewlib.code.model.CodeData
import java.util.ArrayList

/**
 * https://en.wikipedia.org/wiki/Code_128
 * http://www.barcodeisland.com/code128.phtml
 *
 * Created by Thanos Mavroidis on 27/08/2018.
 */
class Code128bBarcode : Barcode {
    private val modulesPerCharacter = 11
    private val modulesInQuietZone = 10
    private val modulesInTerminator = 2
    private val code128bCheckSymbolModulo = 103

    private val startCodeB = CodeData(104, arrayOf(true, true, false, true, false, false, true, false, false, false, false))
    private val stopPattern = arrayOf(true, true, false, false, false, true, true, true, false, true, false)
    private val terminatorPattern = arrayOf(true, true)

    private val code128bData = Code128BData()
    private val map = code128bData.map

    override fun textSupported(text: String): Boolean = text.asSequence().all { map.containsKey(it) }

    override fun fitsInWidth(text: String, width: Int): Boolean = (width/ numberOfModules(text) > 0) // Module width is at least one width unit.

    override fun numberOfModules(text: String): Int {
        // Quite Zone + Start + Encoded Characters + Check + Stop + Terminator + Quiet Zone
        return modulesInQuietZone + modulesPerCharacter + modulesPerCharacter*text.length + modulesPerCharacter + modulesPerCharacter + modulesInTerminator + modulesInQuietZone
    }

    override fun generateBarcode(text: String, width: Int): Array<Pair<Int, Int>> {
        if (argumentsAreInvalid(text, width)) {
            return emptyArray()
        }

        val numberOfModules = numberOfModules(text)
        val moduleWidth = width/numberOfModules

        val list = ArrayList<Pair<Int, Int>>()
        var offset = (width%numberOfModules)/2 + modulesInQuietZone*moduleWidth

        val startAddition = computeBarBorders(moduleWidth, offset, startCodeB.pattern)
        list.addAll(startAddition.first)
        offset = startAddition.second

        text.forEach { c ->
            val charAddition = computeBarBorders(moduleWidth, offset, map[c]!!.pattern)
            list.addAll(charAddition.first)
            offset = charAddition.second
        }

        val check = checkSymbol(text)
        val checkAddition = computeBarBorders(moduleWidth, offset, check.pattern)
        list.addAll(checkAddition.first)
        offset = checkAddition.second

        val stopAddition = computeBarBorders(moduleWidth, offset, stopPattern)
        list.addAll(stopAddition.first)
        offset = stopAddition.second

        val terminatorAddition = computeBarBorders(moduleWidth, offset, terminatorPattern)
        list.addAll(terminatorAddition.first)
        offset = terminatorAddition.second

        return list.toTypedArray()
    }

    private fun argumentsAreInvalid(text: String, width: Int): Boolean {
        val valid = fitsInWidth(text, width) && textSupported(text)

        return !(valid)
    }

    private fun computeBarBorders(moduleWidth: Int, start: Int, pattern: Array<Boolean>): Pair<Array<Pair<Int, Int>>, Int> {
        var offset = start
        val list = ArrayList<Pair<Int, Int>>()

        var left = 0
        pattern.forEach {
            if (it && left == 0) {
                left = offset
            }

            if (!it && left != 0) {
                list.add(Pair(left, offset))
                left = 0
            }

            offset += moduleWidth
        }

        if (left != 0) {
            list.add(Pair(left, offset))
        }

        return Pair(list.toTypedArray(), offset)
    }

    private fun checkSymbol(text: String): CodeData {
        var sum = startCodeB.value
        text.forEachIndexed { i, c ->
            sum += (i + 1)*map[c]!!.value
        }

        return code128bData.checkSymbol(sum%code128bCheckSymbolModulo)
    }
}