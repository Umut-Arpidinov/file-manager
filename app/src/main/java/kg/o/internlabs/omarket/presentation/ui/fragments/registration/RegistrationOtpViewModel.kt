package kg.o.internlabs.omarket.presentation.ui.fragments.registration

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.Register
import kg.o.internlabs.omarket.domain.usecases.CheckOtpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationOtpViewModel @Inject constructor(
    private val useCase: CheckOtpUseCase
) : BaseViewModel() {
    private val _movieState = MutableStateFlow<ApiState<Register>>(ApiState.Loading)
    val movieState = _movieState.asStateFlow()

    fun checkOtp(reg: Register) {
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