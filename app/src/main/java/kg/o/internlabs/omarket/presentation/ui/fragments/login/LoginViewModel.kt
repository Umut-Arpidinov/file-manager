package kg.o.internlabs.omarket.presentation.ui.fragments.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.usecases.CheckNumberPrefs
import kg.o.internlabs.omarket.domain.usecases.CheckOtpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: CheckOtpUseCase, private val checkNumberPrefs: CheckNumberPrefs
) : BaseViewModel() {

    private val _movieState = MutableStateFlow<ApiState<RegisterDto>>(ApiState.Loading)
    private val _ate = MutableStateFlow(false)
    val ate = _ate.asStateFlow()
    val movieState = _movieState.asStateFlow()

    fun checkNumber(number: String) {
        viewModelScope.launch {
            checkNumberPrefs().collectLatest {
                if (it != null) {
                    _ate.value = (it == formattedValues(number))
                }
            }
        }
    }

    private fun formattedValues(number: String): String {
        return number.filter {
            !it.isWhitespace() && it.isDigit()
        }
    }

    fun loginUser(reg: RegisterEntity) {
        viewModelScope.launch {
            useCase(reg).collectLatest {
                when (it) {
                    is ApiState.Success -> _movieState.value = it
                    is ApiState.Failure -> _movieState.value = it
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }
}
