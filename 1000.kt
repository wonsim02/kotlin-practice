// https://www.acmicpc.net/problem/1000

fun main() {
    var input = readLine()!!
    println(input.split(" ").map{ it.toInt() }.sum())
}