package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.usecases.CheckOtpUseCase
import kg.o.internlabs.omarket.domain.usecases.RegisterUserUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.SaveAccessTokenToPrefsUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.SaveLoginStatusToPrefsUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.SaveNumberToPrefsUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.SaveRefreshTokenToPrefsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val checkOtpUseCase: CheckOtpUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val saveNumberToPrefsUseCase: SaveNumberToPrefsUseCase,
    private val saveRefreshTokenToPrefsUseCase: SaveRefreshTokenToPrefsUseCase,
    private val saveAccessTokenToPrefsUseCase: SaveAccessTokenToPrefsUseCase,
    private val saveLoginStatusToPrefsUseCase: SaveLoginStatusToPrefsUseCase
) : BaseViewModel() {
    private val _checkOtp = MutableStateFlow<ApiState<RegisterEntity>>(ApiState.Loading)
    private val _registerUser = MutableStateFlow<ApiState<RegisterEntity>>(ApiState.Loading)
    val checkOtp = _checkOtp.asStateFlow()
    val registerUser = _registerUser.asStateFlow()

    fun checkOtp(reg: RegisterEntity) {
        viewModelScope.launch {
            checkOtpUseCase(reg).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        //_checkOtp.value = it
                        it.data.refreshToken?.let { it1 -> saveRefreshTokenToPrefsUseCase.invoke(it1) }
                        it.data.accessToken?.let { it1 -> saveAccessTokenToPrefsUseCase.invoke(it1) }
                        saveLoginStatusToPrefsUseCase.invoke(true)
                    }
                    is ApiState.Failure -> _checkOtp.value = it
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    fun registerUser(reg: RegisterEntity) {
        viewModelScope.launch {
            registerUserUseCase(reg).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        //_registerUser.value = it
                    }
                    is ApiState.Failure -> {
                        _registerUser.value = it
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    fun saveNumberToPrefs(number: String) {
        saveNumberToPrefsUseCase.invoke(number)
    }

    fun formattedValues(number: String) = number.filter {
        it.isWhitespace().not().and(it.isDigit())
    }
}