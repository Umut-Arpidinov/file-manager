package kg.o.internlabs.omarket.presentation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.CategoriesEntity
import kg.o.internlabs.omarket.domain.entity.ads.AdsByCategory
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.domain.usecases.GetAdsUseCase
import kg.o.internlabs.omarket.domain.usecases.GetCategoriesUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.CheckNumberFromPrefsUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val getAdsUseCase: GetAdsUseCase,
    private val getNumber: CheckNumberFromPrefsUseCase
) :
    BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _number = MutableStateFlow("")
    val number = _number.asStateFlow()

    private val _categories = MutableSharedFlow<ApiState<CategoriesEntity>>()
    val categories = _categories.asSharedFlow()

    private var _ads: Flow<PagingData<ResultX>>? = null
    val ads: Flow<PagingData<ResultX>>?
        get() = _ads

    init {
        getAccessTokenFromPrefs()
    }

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

    fun getAds(adsByCategory: AdsByCategory? = null) = launchPagingAsync({
        getAdsUseCase(getAccessToken(), adsByCategory).cachedIn(viewModelScope)
    }, {
        _ads = it
    })

    private fun getAccessTokenFromPrefs() {
        viewModelScope.launch {
            getAccessTokenFromPrefsUseCase().collectLatest {
                if (it != null) {
                    _token.emit(it)
                    getAds()
                }
            }
        }
    }

    fun getNumberFromPrefs() {
        viewModelScope.launch {
            getNumber().collectLatest {
                if (it != null) {
                    _number.emit(it)
                }
            }
        }
    }

    private fun getAccessToken() = token.value

}