package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.omarket.domain.entity.ads.AdsByCategory
import kg.o.internlabs.omarket.domain.entity.ads.ResultX
import kg.o.internlabs.omarket.domain.usecases.GetAdsUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAdViewModel @Inject constructor(
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val getAdsUseCase: GetAdsUseCase
) : BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private var _ads: Flow<PagingData<ResultX>>? = null
    val ads: Flow<PagingData<ResultX>>?
        get() = _ads

    init {
        getAccessTokenFromPrefs()
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

    private fun getAccessToken() = token.value

}