package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import androidx.lifecycle.viewModelScope
import androidx.loader.content.CursorLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.AvatarDelEntity
import kg.o.internlabs.omarket.domain.entity.AvatarEntity
import kg.o.internlabs.omarket.domain.entity.FAQEntity
import kg.o.internlabs.omarket.domain.entity.MyAdsEntity
import kg.o.internlabs.omarket.domain.usecases.profile_use_cases.*
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
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
    private val deleteAvatarUseCase: DeleteAvatarUseCase
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

    private val _avatar = MutableSharedFlow<ApiState<AvatarEntity>>()
    val avatar = _avatar.asSharedFlow()

    private val _deleteAvatar = MutableSharedFlow<ApiState<AvatarDelEntity>>()
    val deleteAvatar = _deleteAvatar.asSharedFlow()

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

    fun uploadAvatar(body: CursorLoader) {
        viewModelScope.launch {
            uploadAvatarUseCase(getAccessToken(), body).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        println(it.data.toString())
                        _avatar.emit(it)
                    }
                    is ApiState.Failure -> {
                        _avatar.emit(it)
                    }
                    ApiState.Loading -> {
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
                        println(it.data.toString())
                        _deleteAvatar.emit(it)
                    }
                    is ApiState.Failure -> {
                        _deleteAvatar.emit(it)
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