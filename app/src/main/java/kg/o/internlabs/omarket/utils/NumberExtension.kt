package kg.o.internlabs.omarket.utils

fun String.createCurrentNumber(number: String): String {
    val num = number.replace("\\s".toRegex(), "")
    return num.replace("+", "")
}