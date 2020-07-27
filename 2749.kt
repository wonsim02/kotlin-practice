// https://www.acmicpc.net/problem/2749

class Matrix(val elements: List<List<Long>>) {
    val num_rows: Int
    val num_cols: Int
    init {
        if (elements.size == 0 && elements[0].size == 0) {
            throw Exception("Invalid matrix size")
        }

        num_rows = elements.size
        num_cols = elements[0].size
    }

    operator fun times(other: Matrix): Matrix {
        if (this.num_cols != other.num_rows) {
            throw Exception("Invalid multiplication")
        }

        val mul = MutableList(this.num_rows){ _ ->
            MutableList(other.num_cols){ _ -> 0.toLong()}
        }

        (0 until this.num_rows).forEach{ row ->
            (0 until other.num_cols).forEach{ col -> 
                (0 until this.num_cols).forEach{ k ->
                    mul[row][col] += this.elements[row][k]*other.elements[k][col]
                }
            }
        }

        return Matrix(mul.map{ it.map{ elem -> elem % 1000000 } })
    }
}

fun main() {
    val n = readLine()!!.toLong()

    if (n<2) println(n)
    else if (n==2.toLong()) println(1)
    else {
        val bin = (n-1).toString(2)
        val base = Matrix(listOf(listOf<Long>(1, 1), listOf<Long>(1, 0)))
        var pow = base

        (1 until bin.length).forEach{ it ->
            pow *= pow
            if (bin[it]=='1') pow *= base
        }

        println(pow.elements[0][0])
    }
}