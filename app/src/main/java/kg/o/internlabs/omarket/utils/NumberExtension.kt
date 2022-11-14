package kg.o.internlabs.omarket.utils

// Удаляет + и пустоту в editText
// ( Input: +996 500 997 007 Output: 996500997007 )
// Для сета в эдиттекст

fun String.createCurrentNumber(number: String): String {
    val num = number.replace("\\s".toRegex(), "")
    return num.replace("+", "")
}

// Удаляет 996 и пробелы
// Input: +996 500 997 007 Output: 500997007
fun String.deleteCountryCode(number: String): String {
    val num = number.replace("\\s".toRegex(), "")
    return num.replace("+996", "")
}