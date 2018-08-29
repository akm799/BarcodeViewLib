package uk.co.akm.util.barcode.lib.barcodeviewlib.code.data

import uk.co.akm.util.barcode.lib.barcodeviewlib.code.model.CharData
import uk.co.akm.util.barcode.lib.barcodeviewlib.code.model.CodeData
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Thanos Mavroidis on 27/08/2018.
 */
class Code128BData {
    val map: Map<Char, CodeData>

    private val extra: Collection<CodeData>

    init {
        val data = arrayOf(
                CharData(' ',   0, arrayOf(true, true, false, true, true, false, false, true, true, false, false)),
                CharData('!',   1, arrayOf(true, true, false, false, true, true, false, true, true, false, false)),
                CharData('"',   2, arrayOf(true, true, false, false, true, true, false, false, true, true, false)),
                CharData('#',   3, arrayOf(true, false, false, true, false, false, true, true, false, false, false)),
                CharData('$',   4, arrayOf(true, false, false, true, false, false, false, true, true, false, false)),
                CharData('%',   5, arrayOf(true, false, false, false, true, false, false, true, true, false, false)),
                CharData('&',   6, arrayOf(true, false, false, true, true, false, false, true, false, false, false)),
                CharData('\'',  7, arrayOf(true, false, false, true, true, false, false, false, true, false, false)),
                CharData('(',   8, arrayOf(true, false, false, false, true, true, false, false, true, false, false)),
                CharData(')',   9, arrayOf(true, true, false, false, true, false, false, true, false, false, false)),
                CharData('*',  10, arrayOf(true, true, false, false, true, false, false, false, true, false, false)),
                CharData('+',  11, arrayOf(true, true, false, false, false, true, false, false, true, false, false)),
                CharData(',',  12, arrayOf(true, false, true, true, false, false, true, true, true, false, false)),
                CharData('-',  13, arrayOf(true, false, false, true, true, false, true, true, true, false, false)),
                CharData('.',  14, arrayOf(true, false, false, true, true, false, false, true, true, true, false)),
                CharData('/',  15, arrayOf(true, false, true, true, true, false, false, true, true, false, false)),
                CharData('0',  16, arrayOf(true, false, false, true, true, true, false, true, true, false, false)),
                CharData('1',  17, arrayOf(true, false, false, true, true, true, false, false, true, true, false)),
                CharData('2',  18, arrayOf(true, true, false, false, true, true, true, false, false, true, false)),
                CharData('3',  19, arrayOf(true, true, false, false, true, false, true, true, true, false, false)),
                CharData('4',  20, arrayOf(true, true, false, false, true, false, false, true, true, true, false)),
                CharData('5',  21, arrayOf(true, true, false, true, true, true, false, false, true, false, false)),
                CharData('6',  22, arrayOf(true, true, false, false, true, true, true, false, true, false, false)),
                CharData('7',  23, arrayOf(true, true, true, false, true, true, false, true, true, true, false)),
                CharData('8',  24, arrayOf(true, true, true, false, true, false, false, true, true, false, false)),
                CharData('9',  25, arrayOf(true, true, true, false, false, true, false, true, true, false, false)),
                CharData(':',  26, arrayOf(true, true, true, false, false, true, false, false, true, true, false)),
                CharData(';',  27, arrayOf(true, true, true, false, true, true, false, false, true, false, false)),
                CharData('<',  28, arrayOf(true, true, true, false, false, true, true, false, true, false, false)),
                CharData('=',  29, arrayOf(true, true, true, false, false, true, true, false, false, true, false)),
                CharData('>',  30, arrayOf(true, true, false, true, true, false, true, true, false, false, false)),
                CharData('?',  31, arrayOf(true, true, false, true, true, false, false, false, true, true, false)),
                CharData('@',  32, arrayOf(true, true, false, false, false, true, true, false, true, true, false)),
                CharData('A',  33, arrayOf(true, false, true, false, false, false, true, true, false, false, false)),
                CharData('B',  34, arrayOf(true, false, false, false, true, false, true, true, false, false, false)),
                CharData('C',  35, arrayOf(true, false, false, false, true, false, false, false, true, true, false)),
                CharData('D',  36, arrayOf(true, false, true, true, false, false, false, true, false, false, false)),
                CharData('E',  37, arrayOf(true, false, false, false, true, true, false, true, false, false, false)),
                CharData('F',  38, arrayOf(true, false, false, false, true, true, false, false, false, true, false)),
                CharData('G',  39, arrayOf(true, true, false, true, false, false, false, true, false, false, false)),
                CharData('H',  40, arrayOf(true, true, false, false, false, true, false, true, false, false, false)),
                CharData('I',  41, arrayOf(true, true, false, false, false, true, false, false, false, true, false)),
                CharData('J',  42, arrayOf(true, false, true, true, false, true, true, true, false, false, false)),
                CharData('K',  43, arrayOf(true, false, true, true, false, false, false, true, true, true, false)),
                CharData('L',  44, arrayOf(true, false, false, false, true, true, false, true, true, true, false)),
                CharData('M',  45, arrayOf(true, false, true, true, true, false, true, true, false, false, false)),
                CharData('N',  46, arrayOf(true, false, true, true, true, false, false, false, true, true, false)),
                CharData('O',  47, arrayOf(true, false, false, false, true, true, true, false, true, true, false)),
                CharData('P',  48, arrayOf(true, true, true, false, true, true, true, false, true, true, false)),
                CharData('Q',  49, arrayOf(true, true, false, true, false, false, false, true, true, true, false)),
                CharData('R',  50, arrayOf(true, true, false, false, false, true, false, true, true, true, false)),
                CharData('S',  51, arrayOf(true, true, false, true, true, true, false, true, false, false, false)),
                CharData('T',  52, arrayOf(true, true, false, true, true, true, false, false, false, true, false)),
                CharData('U',  53, arrayOf(true, true, false, true, true, true, false, true, true, true, false)),
                CharData('V',  54, arrayOf(true, true, true, false, true, false, true, true, false, false, false)),
                CharData('W',  55, arrayOf(true, true, true, false, true, false, false, false, true, true, false)),
                CharData('X',  56, arrayOf(true, true, true, false, false, false, true, false, true, true, false)),
                CharData('Y',  57, arrayOf(true, true, true, false, true, true, false, true, false, false, false)),
                CharData('Z',  58, arrayOf(true, true, true, false, true, true, false, false, false, true, false)),
                CharData('[',  59, arrayOf(true, true, true, false, false, false, true, true, false, true, false)),
                CharData('\\', 60, arrayOf(true, true, true, false, true, true, true, true, false, true, false)),
                CharData(']',  61, arrayOf(true, true, false, false, true, false, false, false, false, true, false)),
                CharData('^',  62, arrayOf(true, true, true, true, false, false, false, true, false, true, false)),
                CharData('_',  63, arrayOf(true, false, true, false, false, true, true, false, false, false, false)),
                CharData('`',  64, arrayOf(true, false, true, false, false, false, false, true, true, false, false)),
                CharData('a',  65, arrayOf(true, false, false, true, false, true, true, false, false, false, false)),
                CharData('b',  66, arrayOf(true, false, false, true, false, false, false, false, true, true, false)),
                CharData('c',  67, arrayOf(true, false, false, false, false, true, false, true, true, false, false)),
                CharData('d',  68, arrayOf(true, false, false, false, false, true, false, false, true, true, false)),
                CharData('e',  69, arrayOf(true, false, true, true, false, false, true, false, false, false, false)),
                CharData('f',  70, arrayOf(true, false, true, true, false, false, false, false, true, false, false)),
                CharData('g',  71, arrayOf(true, false, false, true, true, false, true, false, false, false, false)),
                CharData('h',  72, arrayOf(true, false, false, true, true, false, false, false, false, true, false)),
                CharData('i',  73, arrayOf(true, false, false, false, false, true, true, false, true, false, false)),
                CharData('j',  74, arrayOf(true, false, false, false, false, true, true, false, false, true, false)),
                CharData('k',  75, arrayOf(true, true, false, false, false, false, true, false, false, true, false)),
                CharData('l',  76, arrayOf(true, true, false, false, true, false, true, false, false, false, false)),
                CharData('m',  77, arrayOf(true, true, true, true, false, true, true, true, false, true, false)),
                CharData('n',  78, arrayOf(true, true, false, false, false, false, true, false, true, false, false)),
                CharData('o',  79, arrayOf(true, false, false, false, true, true, true, true, false, true, false)),
                CharData('p',  80, arrayOf(true, false, true, false, false, true, true, true, true, false, false)),
                CharData('q',  81, arrayOf(true, false, false, true, false, true, true, true, true, false, false)),
                CharData('r',  82, arrayOf(true, false, false, true, false, false, true, true, true, true, false)),
                CharData('s',  83, arrayOf(true, false, true, true, true, true, false, false, true, false, false)),
                CharData('t',  84, arrayOf(true, false, false, true, true, true, true, false, true, false, false)),
                CharData('u',  85, arrayOf(true, false, false, true, true, true, true, false, false, true, false)),
                CharData('v',  86, arrayOf(true, true, true, true, false, true, false, false, true, false, false)),
                CharData('w',  87, arrayOf(true, true, true, true, false, false, true, false, true, false, false)),
                CharData('x',  88, arrayOf(true, true, true, true, false, false, true, false, false, true, false)),
                CharData('y',  89, arrayOf(true, true, false, true, true, false, true, true, true, true, false)),
                CharData('z',  90, arrayOf(true, true, false, true, true, true, true, false, true, true, false)),
                CharData('{',  91, arrayOf(true, true, true, true, false, true, true, false, true, true, false)),
                CharData('|',  92, arrayOf(true, false, true, false, true, true, true, true, false, false, false)),
                CharData('}',  93, arrayOf(true, false, true, false, false, false, true, true, true, true, false)),
                CharData('~',  94, arrayOf(true, false, false, false, true, false, true, true, true, true, false))
        )

        map = HashMap<Char, CodeData>()
        data.forEach { map[it.c] = it }

        extra = ArrayList()
        extra.add(CodeData(95, arrayOf(true, false, true, true, true, true, false, true, false, false, false)))
        extra.add(CodeData(96, arrayOf(true, false, true, true, true, true, false, false, false, true, false)))
        extra.add(CodeData(97, arrayOf(true, true, true, true, false, true, false, true, false, false, false)))
        extra.add(CodeData(98, arrayOf(true, true, true, true, false, true, false, false, false, true, false)))
        extra.add(CodeData(99, arrayOf(true, false, true, true, true, false, true, true, true, true, false)))
        extra.add(CodeData(100, arrayOf(true, false, true, true, true, true, false, true, true, true, false)))
        extra.add(CodeData(101, arrayOf(true, true, true, false, true, false, true, true, true, true, false)))
        extra.add(CodeData(102, arrayOf(true, true, true, true, false, true, false, true, true, true, false)))
    }

    fun symbolForValue(value: Int): CodeData {
        val matcher = { codeData: CodeData ->  codeData.value == value}

        return if (extra.any(matcher)) extra.first(matcher) else map.values.first(matcher)
    }
}