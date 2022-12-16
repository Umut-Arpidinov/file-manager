package kg.o.internlabs.omarket.presentation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.AdsDto
import kg.o.internlabs.omarket.domain.entity.CategoriesEntity
import kg.o.internlabs.omarket.domain.entity.ads.AdsByCategory
import kg.o.internlabs.omarket.domain.entity.ads.MainResult
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.domain.usecases.GetAdsByCategoryUseCase
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
    private val getAdsUseCase: GetAdsUseCase,
    private val getAdsByCategoryUseCase: GetAdsByCategoryUseCase
) :
    BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _categories = MutableSharedFlow<ApiState<CategoriesEntity>>()
    val categories = _categories.asSharedFlow()

    private lateinit var _ads: Flow<PagingData<ResultX>>
    val ads: Flow<PagingData<ResultX>>
        get() = _ads

    private lateinit var _adsByCategory: Flow<PagingData<MainResult>>
    val adsByCategory: Flow<PagingData<MainResult>>
        get() = _adsByCategory

    init {
        getAccessTokenFromPrefs()
    }

    private fun getCategories() {
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
    },{
        _ads = it
    })
/*
    fun getAdsByCategory(ads: AdsByCategory) = launchPagingAsync({
        getAdsByCategoryUseCase(getAccessToken(), ads).cachedIn(viewModelScope)
    },{
        _adsByCategory = it
    })*/

    private fun getAccessTokenFromPrefs() {
        viewModelScope.launch {
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
                    getCategories()
                    getAds()
                }
            }
        }
    }

    private fun getAccessToken() = token.value
}