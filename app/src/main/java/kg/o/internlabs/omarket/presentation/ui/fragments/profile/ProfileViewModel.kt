package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.FAQEntity
import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kg.o.internlabs.omarket.domain.usecases.profile_use_cases.GetFaqUseCase
import kg.o.internlabs.omarket.domain.usecases.profile_use_cases.GetMyActiveAdsUseCase
import kg.o.internlabs.omarket.domain.usecases.profile_use_cases.GetMyAdsUseCase
import kg.o.internlabs.omarket.domain.usecases.profile_use_cases.GetMyNonActiveAdsUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val faqUseCase: GetFaqUseCase,
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val getMyActiveAdsUseCase: GetMyActiveAdsUseCase,
    private val getMyAdsUseCase: GetMyAdsUseCase,
    private val getMyNonActiveAdsUseCase: GetMyNonActiveAdsUseCase
) :
    BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _faqs = MutableSharedFlow<ApiState<FAQEntity>>()
    val faqs = _faqs.asSharedFlow()

    private val _activeAds = MutableSharedFlow<ApiState<MyAdsEntity>>()
    val activeAds = _activeAds.asSharedFlow()

    private val _nonActiveAds = MutableSharedFlow<ApiState<MyAdsEntity>>()
    val nonActiveAds = _nonActiveAds.asSharedFlow()

    private val _allAds = MutableSharedFlow<ApiState<MyAdsEntity>>()
    val allAds = _allAds.asSharedFlow()

    fun getFaq() {
        viewModelScope.launch {
            faqUseCase(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _faqs.emit(it)
                    }
                    is ApiState.Failure -> {
                        _faqs.emit(it)
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    fun getAllAds() {
        viewModelScope.launch {
            getMyAdsUseCase(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _allAds.emit(it)
                    }
                    is ApiState.Failure -> {
                        _allAds.emit(it)
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    fun getActiveAds() {
        viewModelScope.launch {
            getMyActiveAdsUseCase(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _activeAds.emit(it)
                    }
                    is ApiState.Failure -> {
                        _activeAds.emit(it)
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    fun getNonActiveAds() {
        viewModelScope.launch {
            getMyNonActiveAdsUseCase(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _nonActiveAds.emit(it)
                    }
                    is ApiState.Failure -> {
                        _nonActiveAds.emit(it)
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    fun getAccessTokenFromPrefs() {
        viewModelScope.launch{
            getAccessTokenFromPrefsUseCase().collectLatest {
                if (it != null) {
                    _token.emit(it)
                }
            }
        }
    }

    private fun getAccessToken() = token.value
}