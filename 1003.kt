// https://www.acmicpc.net/problem/1003

fun main() {
    val t = readLine()!!.toInt()
    val MAX_N = 40
    val pairs = mutableListOf(arrayOf(1, 0), arrayOf(0, 1))

    (2..MAX_N).forEach{ it ->
        var a = pairs[it-2]
        var b = pairs[it-1]
        pairs.add(arrayOf(a[0]+b[0], a[1]+b[1]))
    }

    (1..t).forEach{ _ ->
        var i = readLine()!!.toInt()
        println(pairs[i].joinToString(separator=" "))
    }
}