package kg.o.internlabs.omarket.domain.repository

import kotlinx.coroutines.flow.Flow

interface PrefsRepository {

    fun checkPhoneNumberFromPrefs(): Flow<String?>

}