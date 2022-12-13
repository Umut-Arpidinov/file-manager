package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import androidx.lifecycle.viewModelScope
import androidx.loader.content.CursorLoader
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.*
import kg.o.internlabs.omarket.domain.usecases.profile_use_cases.*
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAvatarUrlFromPrefsUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.SaveAvatarUrlToPrefsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val faqUseCase: GetFaqUseCase,
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val getMyActiveAdsUseCase: GetMyActiveAdsUseCase,
    private val getMyNonActiveAdsUseCase: GetMyNonActiveAdsUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val deleteAvatarUseCase: DeleteAvatarUseCase,
    private val saveAvatarUrlToPrefsUseCase: SaveAvatarUrlToPrefsUseCase,
    private val getAvatarUrlFromPrefsUseCase: GetAvatarUrlFromPrefsUseCase,
    private val getMyAllAdsUseCase: GetMyAllAdsUseCase
) :
    BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _faqs = MutableSharedFlow<ApiState<FAQEntity>>()
    val faqs = _faqs.asSharedFlow()

    private lateinit var _activeAds: Flow<PagingData<MyAdsResultsEntity>>
    val activeAds: Flow<PagingData<MyAdsResultsEntity>>
        get() = _activeAds

    private lateinit var _nonActiveAds: Flow<PagingData<MyAdsResultsEntity>>
    val nonActiveAds: Flow<PagingData<MyAdsResultsEntity>>
        get() = _nonActiveAds

    private val _avatar = MutableSharedFlow<ApiState<AvatarEntity>>()
    val avatar = _avatar.asSharedFlow()

    private val _allAds = MutableSharedFlow<ApiState<MyAdsEntity>>()
    val allAds = _allAds.asSharedFlow()

    private val _deleteAvatar = MutableSharedFlow<ApiState<AvatarDelEntity>>()
    val deleteAvatar = _deleteAvatar.asSharedFlow()

    private val _avatarUrl = MutableStateFlow("")
    val avatarUrl = _avatarUrl.asStateFlow()


    init {
        getAccessTokenFromPrefs()
        getAvatarUrlFromPrefs()
    }

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
                        _faqs.emit(it)
                    }
                }
            }
        }
    }

    private fun getActiveAds() = launchPagingAsync({
        getMyActiveAdsUseCase(getAccessToken()).cachedIn(viewModelScope)
    }, {
        _activeAds = it
    })

    private fun getNonActiveAds() = launchPagingAsync({
            getMyNonActiveAdsUseCase(getAccessToken()).cachedIn(viewModelScope)
    }, {
        _nonActiveAds = it
    })


    private fun getMyAllAds() {
        viewModelScope.launch {
            getMyAllAdsUseCase(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _allAds.emit(it)
                    }
                    is ApiState.Failure -> {
                        _allAds.emit(it)
                    }
                    ApiState.Loading -> {
                        _allAds.emit(it)
                    }
                }
            }
        }
    }

    fun uploadAvatar(body: CursorLoader) {
        viewModelScope.launch {
            uploadAvatarUseCase(getAccessToken(), body).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _avatar.emit(it)
                        it.data.result?.url?.let { it1 -> setAvatarUrlToPrefs(it1) }
                    }
                    is ApiState.Failure -> {
                        _avatar.emit(it)
                    }
                    ApiState.Loading -> {
                        _avatar.emit(it)
                    }
                }
            }
        }
    }

    fun deleteAvatar() {
        viewModelScope.launch {
            deleteAvatarUseCase(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _deleteAvatar.emit(it)
                        setAvatarUrlToPrefs(null)
                    }
                    is ApiState.Failure -> {
                        _deleteAvatar.emit(it)
                    }
                    ApiState.Loading -> {
                        _deleteAvatar.emit(it)
                    }
                }
            }
        }
    }

    private fun getAccessTokenFromPrefs() {
        viewModelScope.launch {
            getAccessTokenFromPrefsUseCase().collectLatest {
                if (it != null) {
                    _token.emit(it)
                    getActiveAds()
                    getNonActiveAds()
                    getMyAllAds()
                }
            }
        }
    }

    private fun getAvatarUrlFromPrefs() {
        viewModelScope.launch {
            getAvatarUrlFromPrefsUseCase().collectLatest {
                if (it != null) {
                    _avatarUrl.emit(it)
                }
            }
        }
    }

    private fun setAvatarUrlToPrefs(avatarUrl: String?) {
        saveAvatarUrlToPrefsUseCase.invoke(avatarUrl)
    }

    private fun getAccessToken() = token.value
}