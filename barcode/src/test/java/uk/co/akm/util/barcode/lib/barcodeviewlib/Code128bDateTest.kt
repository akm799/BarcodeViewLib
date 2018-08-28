package uk.co.akm.util.barcode.lib.barcodeviewlib

import org.junit.Assert
import org.junit.Test
import uk.co.akm.util.barcode.lib.barcodeviewlib.code.data.Code128BData
import uk.co.akm.util.barcode.lib.barcodeviewlib.code.model.CodeData
import java.io.File

/**
 * Test file contains Barcode Code 128 B data taken from the table in
 * https://en.wikipedia.org/wiki/Code_128
 *
 * Created by Thanos Mavroidis on 28/08/2018.
 */
class Code128bDateTest {
    private val valueIndex = 0
    private val charIndex = 2
    private val patternIndex = 6
    private val maxPrintableCharIndex = 94
    private val code128DataFile = "./src/test/res/code128b.dat"

    private val underTest = Code128BData()

    @Test
    fun shouldHaveCode128bData() {
        File(code128DataFile).forEachLine {
            val parts = it.split("\t")
            val value = parts[valueIndex].toInt()
            val pattern = parts[patternIndex]
            if (isPrintableChar(value)) {
                val c = if (parts[charIndex] == "space") ' ' else parts[charIndex][0]
                assertCharData(c, value, pattern)
            } else {
                assertCodeData(value, pattern)
            }
        }
    }

    private fun isPrintableChar(value: Int) = (value >=0 && value <= maxPrintableCharIndex)

    private fun assertCodeData(value: Int, pattern: String) {
        assertCodeData(value, pattern, underTest.checkSymbol(value))
    }

    private fun assertCharData(c: Char, value: Int, pattern: String) {
        Assert.assertTrue(underTest.map.containsKey(c))
        assertCodeData(value, pattern, underTest.map[c])
    }

    private fun assertCodeData(value: Int, pattern: String, data: CodeData?) {
        Assert.assertNotNull(data)
        if (data != null) {
            Assert.assertEquals(value, data.value)
            assertPattern(pattern, data)
        }
    }

    private fun assertPattern(pattern: String, data: CodeData) {
        Assert.assertEquals(pattern.length, data.pattern.size)
        for (i in 0 until pattern.length) {
            Assert.assertEquals('1' == pattern[i],  data.pattern[i])
            Assert.assertEquals('0' == pattern[i], !data.pattern[i])
        }
    }
}