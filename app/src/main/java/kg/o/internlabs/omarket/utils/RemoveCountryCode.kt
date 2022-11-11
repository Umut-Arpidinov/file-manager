package kg.o.internlabs.omarket.utils

fun String.removeCountryCode(number: String): String {
    return number.replace("+996", "")
}