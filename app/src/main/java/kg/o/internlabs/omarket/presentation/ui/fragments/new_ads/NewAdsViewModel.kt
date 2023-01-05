package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.InitiateAdEntity
import kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases.InitiateAdUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAdsViewModel @Inject constructor(
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val initiateAd: InitiateAdUseCase
) : BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _uuid = MutableSharedFlow<ApiState<InitiateAdEntity>>()
    val uuid = _uuid.asSharedFlow()

    init {
        getAccessTokenFromPrefs()
    }

    private fun getAccessTokenFromPrefs() {
        viewModelScope.launch {
            getAccessTokenFromPrefsUseCase().collectLatest {
                if (it != null) {
                    _token.emit(it)
                    getInitiatedAd()
                }
            }
        }
    }

    private fun getInitiatedAd() {
        viewModelScope.launch {
            initiateAd(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _uuid.emit(it)
                    }
                    is ApiState.Failure -> {
                        _uuid.emit(it)
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    private fun getAccessToken() = token.value

}