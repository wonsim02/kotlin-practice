// https://www.acmicpc.net/problem/2748

fun main() {
    val n = readLine()!!.toInt()

    if (n<=1) {
        println(n)
    }
    else {
        println((2..n).fold<Int, List<Long>>(listOf(0, 1)){ R, _ ->
            listOf(R[1], R.sum())
        }[1])
    }
}