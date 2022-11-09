package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.usecases.CheckOtpUseCase
import kg.o.internlabs.omarket.domain.usecases.RegisterUserUseCase
import kg.o.internlabs.omarket.toDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val checkOtpUseCase: CheckOtpUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {
    private val _checkOtp = MutableStateFlow<ApiState<RegisterDto>>(ApiState.Loading)
    private val _registerUser = MutableStateFlow<ApiState<RegisterDto>>(ApiState.Loading)
    val checkOtp = _checkOtp.asStateFlow()
    val registerUser = _registerUser.asStateFlow()

    fun checkOtp(reg: RegisterEntity) {
        viewModelScope.launch {
            checkOtpUseCase(reg).collectLatest {
                when (it) {
                    is ApiState.Success -> _checkOtp.value = it
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
                    is ApiState.Success -> _registerUser.value = it
                    is ApiState.Failure -> _registerUser.value = it
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }
}