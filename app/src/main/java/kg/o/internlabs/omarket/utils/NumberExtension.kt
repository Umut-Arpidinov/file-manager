package kg.o.internlabs.omarket.utils

fun String.createCurrentNumber(number: String): String {
    val num = number.replace("\\s".toRegex(), "")
    return num.replace("+", "")
}

fun String.delete996(number: String): String {
    val num = number.replace("\\s".toRegex(), "")
    return num.replace("+996", "")
}