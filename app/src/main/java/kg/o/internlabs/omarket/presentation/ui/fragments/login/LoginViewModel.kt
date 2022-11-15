package kg.o.internlabs.omarket.presentation.ui.fragments.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.usecases.LoginUserUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUserUseCase,
    private val checkNumberPrefsUseCase: CheckNumberPrefsUseCase,
    private val checkPasswordPrefsUseCase: CheckPasswordPrefsUseCase,
    private val putAccessTokenPrefsUseCase: PutAccessTokenPrefsUseCase,
    private val putNumberPrefsUseCase: PutNumberPrefsUseCase,
    private val putPasswordPrefsUseCase: PutPasswordPrefsUseCase,
    private val putRefreshTokenPrefsUseCase: PutRefreshTokenPrefsUseCase,
    private val setLoginStatusUseCase: SetLoginStatusUseCase
) : BaseViewModel() {

    private val _movieState = MutableStateFlow<ApiState<RegisterDto>>(ApiState.Loading)
    private val _num = MutableStateFlow(false)
    private val _pwd = MutableStateFlow(false)
    val movieState = _movieState.asStateFlow()
    val num = _num.asStateFlow()
    val pwd = _pwd.asStateFlow()

    fun checkNumber(number: String) {
        viewModelScope.launch {
            checkNumberPrefsUseCase().collectLatest {
                if (it != null) {
                    _num.value = (it == formattedValues(number))
                }
            }
        }
    }

    fun checkPassword(pwd: String) {
        viewModelScope.launch {
            checkPasswordPrefsUseCase().collectLatest {
                if (it != null) {
                    _pwd.value = (it == pwd)
                }
            }
        }
    }

    fun putNumber(number: String) {
        putNumberPrefsUseCase.invoke(number)
    }

    fun putPwd(pwd: String) {
        putPasswordPrefsUseCase.invoke(pwd)
    }


    fun formattedValues(number: String): String {
        return number.filter {
            !it.isWhitespace() && it.isDigit()
        }
    }

    fun loginUser(reg: RegisterEntity) {
        viewModelScope.launch {
            useCase(reg).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _movieState.value = it
                        it.data.refreshToken?.let { it1 ->
                            putRefreshTokenPrefsUseCase.invoke(it1)
                        }
                        it.data.accessToken?.let { it1 ->
                            putAccessTokenPrefsUseCase.invoke(it1)
                        }
                        setLoginStatusUseCase.invoke(true)
                    }
                    is ApiState.Failure -> {
                        setLoginStatusUseCase.invoke(false)
                        _movieState.value = it
                    }
                    is ApiState.Loading -> {
                        setLoginStatusUseCase.invoke(false)
                    }
                }
            }
        }
    }
}
