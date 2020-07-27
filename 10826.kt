// https://www.acmicpc.net/problem/10826

import kotlin.math.max
import kotlin.math.pow

val NUM_DIGITS = 9
val UPPER_LIMIT = 10.toDouble().pow(NUM_DIGITS).toLong()
val STRING_FORMAT = "%%0%dd".format(NUM_DIGITS)

class MyInt(val digits: List<Long>) {
    val numDigits: Int
    init {
        if (digits.size == 0) throw Exception("Invalid number of digits")
        numDigits = digits.size
    }

    operator fun plus(other: MyInt): MyInt {
        val newNumDigits = max(this.numDigits, other.numDigits)+1
        val newDigits = MutableList(newNumDigits){ if (it<this.numDigits)this.digits[it] else 0 }

        (0 until other.numDigits).forEach{ it ->
            newDigits[it] += other.digits[it]
            if (newDigits[it]> UPPER_LIMIT-1) {
                newDigits[it] -= UPPER_LIMIT
                newDigits[it+1] += 1.toLong()
            }
        }

        var idx = newNumDigits-1
        while (idx>0 && newDigits[idx] == 0.toLong()) {
            newDigits.removeAt(idx)
            idx--
        }

        return MyInt(newDigits)
    }

    operator fun times(other: MyInt): MyInt {
        if (this.isZero() || other.isZero()) return MyInt(listOf(0))

        val newNumDigits = this.numDigits + other.numDigits + 1
        val newDigits = MutableList(newNumDigits){ _ -> 0.toLong()}

        (0 until other.numDigits).forEach{ j ->
            (0 until this.numDigits).forEach{ i ->
                newDigits[i+j] += this.digits[i]*other.digits[j]
                newDigits[i+j+1] += newDigits[i+j]/UPPER_LIMIT
                newDigits[i+j] %= UPPER_LIMIT
            }
        }

        var idx = newNumDigits-1
        while (idx>0 && newDigits[idx]==0.toLong()) {
            newDigits.removeAt(idx)
            idx --
        }

        return MyInt(newDigits)
    }

    override fun toString(): String {
        var result = this.digits[this.numDigits-1].toString()
        if (numDigits>1)
            (this.numDigits-2 downTo 0).forEach{ result += STRING_FORMAT.format(this.digits[it]) }
        return result
    }

    fun isZero() = (numDigits==1 && digits[0]==0.toLong())
}

class MyMatrix(val elements: List<List<MyInt>>) {
    val numRows: Int
    val numCols: Int
    init {
        if (elements.size == 0 && elements[0].size == 0) {
            throw Exception("Invalid matrix size")
        }

        numRows = elements.size
        numCols = elements[0].size
    }

    operator fun times(other: MyMatrix): MyMatrix {
        if (this.numCols != other.numRows) {
            throw Exception("Invalid multiplication")
        }

        val zero = MyInt(listOf(0))
        val mul = MutableList(this.numRows){ _ ->
            MutableList(other.numCols){ _ -> zero}
        }

        (0 until this.numRows).forEach{ row ->
            (0 until other.numCols).forEach{ col -> 
                (0 until this.numCols).forEach{ k ->
                    mul[row][col] += this.elements[row][k]*other.elements[k][col]
                }
            }
        }

        return MyMatrix(mul)
    }
}

fun main() {
    val n = readLine()!!.toInt()

    if (n<2) println(n)
    else if (n==2) println(1)
    else {
        val one = MyInt(listOf(1))
        val zero = MyInt(listOf(0))

        val bin = (n-1).toString(2)
        val base = MyMatrix(listOf(listOf(one, one), listOf(one, zero)))
        var pow = base

        (1 until bin.length).forEach{ it ->
            pow *= pow
            if (bin[it]=='1') pow *= base
        }

        println(pow.elements[0][0].toString())
    }
}