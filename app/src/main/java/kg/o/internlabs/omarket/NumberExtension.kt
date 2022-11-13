package kg.o.internlabs.omarket

fun String.delete996(number: String): String {
    val num = number.replace("\\s".toRegex(), "")
    return num.replace("+996", "")
}