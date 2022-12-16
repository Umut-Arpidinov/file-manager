package kg.o.internlabs.omarket.presentation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.AdsDto
import kg.o.internlabs.omarket.domain.entity.CategoriesEntity
import kg.o.internlabs.omarket.domain.usecases.GetAdsUseCase
import kg.o.internlabs.omarket.domain.usecases.GetCategoriesUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val getAdsUseCase: GetAdsUseCase
) :
    BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _categories = MutableSharedFlow<ApiState<CategoriesEntity>>()
    val categories = _categories.asSharedFlow()

    private val _ads = MutableSharedFlow<ApiState<AdsDto?>>()
    val ads = _ads.asSharedFlow()

    fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _categories.emit(it)

                    }
                    is ApiState.Failure -> {
                        _categories.emit(it)
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    fun getAds(page: Int) {
        viewModelScope.launch {
            getAdsUseCase(
                page,
                "eyJ1c2VyX2lkIjogNjUsICJ1dWlkIjogIjZjZmRhY2JhZjFlYzQ4ODZiZGI2MGU1MWIwOTEwZmZmIiwgImV4X3RpbWUiOiAxNjczMTEwNTI1fQ==:5YUyZJF1xLQ1K62GwPoYcJH8Ysc.6cfdacbaf1ec4886bdb60e51b0910fff"
            ).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _ads.emit(it)
                    }
                    is ApiState.Failure -> {
                        _ads.emit(it)

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