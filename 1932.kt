// https://www.acmicpc.net/problem/1932

fun main() {
    val n = readLine()!!.toInt()
    val tri = (1..n).map{ tmp ->
        readLine()!!.split(" ").map{ it.toInt() }
    }
    val len = tri.fold<List<Int>, List<Int>>(listOf()){ R, T ->
        (1..T.size).map{ it ->
            var elem = T[it-1]
            if (it<T.size && elem<T[it-1]+R[it-1]) {
                elem = T[it-1]+R[it-1]
            }
            if (it>1 && elem<T[it-1]+R[it-2]) {
                elem = T[it-1]+R[it-2]
            }
            elem
        }
    }
    println(len.max())
}