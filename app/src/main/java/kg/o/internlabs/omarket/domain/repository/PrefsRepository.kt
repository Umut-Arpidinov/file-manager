package kg.o.internlabs.omarket.domain.repository

interface PrefsRepository {

    fun checkPhoneNumberFromPrefs(number: String): Boolean

}