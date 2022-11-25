package kg.o.internlabs.omarket.presentation.ui.fragments.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.usecases.LoginUserUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUserUseCase,
    private val saveAccessTokenToPrefsUseCase: SaveAccessTokenToPrefsUseCase,
    private val saveNumberToPrefsUseCase: SaveNumberToPrefsUseCase,
    private val saveRefreshTokenToPrefsUseCase: SaveRefreshTokenToPrefsUseCase,
    private val saveLoginStatusToPrefsUseCase: SaveLoginStatusToPrefsUseCase,
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase
) : BaseViewModel() {

    private val _movieState = MutableStateFlow<ApiState<RegisterDto>>(ApiState.Loading)
    val movieState = _movieState.asStateFlow()

    private val _isTokenSavedState = MutableSharedFlow<Boolean>()
    val isTokenSavedState = _isTokenSavedState.asSharedFlow()

    fun saveNumberToPrefs(number: String) {
        saveNumberToPrefsUseCase.invoke(formattedValues(number))
    }

    fun formattedValues(number: String) = number.filter {
        it.isWhitespace().not().and(it.isDigit())
    }

    fun getTokenState() {
        viewModelScope.launch {
            getAccessTokenFromPrefsUseCase().collect{
                _isTokenSavedState.emit(it.isNullOrEmpty().not())
            }
        }
    }

    fun loginUser(reg: RegisterEntity) {
        viewModelScope.launch {
            useCase(reg).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _movieState.value = it
                        it.data.refreshToken?.let { it1 ->
                            saveRefreshTokenToPrefsUseCase.invoke(it1)
                        }
                        it.data.accessToken?.let { it1 ->
                            saveAccessTokenToPrefsUseCase.invoke(it1)
                        }
                        saveLoginStatusToPrefsUseCase.invoke(true)
                    }
                    is ApiState.Failure -> {
                        saveLoginStatusToPrefsUseCase.invoke(false)
                        _movieState.value = it
                    }
                    is ApiState.Loading -> {
                        saveLoginStatusToPrefsUseCase.invoke(false)
                    }
                    else -> {
                        saveLoginStatusToPrefsUseCase.invoke(false)
                        _movieState.value = it
                    }
                }
            }
        }
    }
}
