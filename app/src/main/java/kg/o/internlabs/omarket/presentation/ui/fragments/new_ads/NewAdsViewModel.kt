package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.*
import kg.o.internlabs.omarket.domain.usecases.GetCategoriesUseCase
import kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases.*
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewAdsViewModel @Inject constructor(
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val initiateAd: InitiateAdUseCase,
    private val uploadImageToAdUseCase: UploadImageToAdUseCase,
    private val deleteImageFromAdUseCase: DeleteImageFromAdUseCase,
    private val editAnAdUseCase: EditAnAdUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAdTypeUseCase: AdTypeUseCase
) : BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _uuid = MutableStateFlow("")
    val uuid = _uuid.asStateFlow()

    private val _uploadImage = MutableSharedFlow<ApiState<UploadImageEntity>>()
    val uploadImage = _uploadImage.asSharedFlow()

    private val _editedAd = MutableSharedFlow<ApiState<EditAds>>()
    val editedAd = _editedAd.asSharedFlow()

    private val _categories = MutableSharedFlow<ApiState<CategoriesEntity>>()
    val categories = _categories.asSharedFlow()

    private val _adType = MutableSharedFlow<ApiState<AdTypeEntity>>()
    val adType = _adType.asSharedFlow()

    private val _deleteImage = MutableSharedFlow<ApiState<DeleteImageEntity>>()

    init {
        getAccessTokenFromPrefs()
    }

    fun initViewModel() {
        getCategories()
    }

    private fun getAccessTokenFromPrefs() {
        viewModelScope.launch {
            getAccessTokenFromPrefsUseCase().collectLatest {
                if (it != null) {
                    _token.emit(it)
                    getInitiatedAd()
                    getAdType()
                }
            }
        }
    }

    fun uploadImage(uri: File?) {
        viewModelScope.launch {
            uploadImageToAdUseCase(getAccessToken(), uri, getUuid())
                .collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _uploadImage.emit(it)
                    }
                    is ApiState.Failure -> {
                        _uploadImage.emit(it)
                    }
                    ApiState.Loading -> {
                        _uploadImage.emit(it)
                    }
                }
            }
        }
    }

    fun createAd(editAds: EditAds) {
        viewModelScope.launch {
            editAnAdUseCase(getAccessToken(), editAds, getUuid())
                .collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _editedAd.emit(it)
                    }
                    is ApiState.Failure -> {
                        _editedAd.emit(it)
                    }
                    ApiState.Loading -> {
                        _editedAd.emit(it)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        println("vOnDest------")
        super.onCleared()
    }

    fun deleteImageFromAd(body: DeletedImageUrlEntity) {
        viewModelScope.launch {
            deleteImageFromAdUseCase(getAccessToken(), body, getUuid()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        println("----Del is ok")
                        _deleteImage.emit(it)
                    }
                    is ApiState.Failure -> {
                        println("-----Del is fail    ${it.msg.message}")
                        _deleteImage.emit(it)
                    }
                    ApiState.Loading -> {
                        _deleteImage.emit(it)
                    }
                }
            }
        }
    }


    private fun getInitiatedAd() {
        viewModelScope.launch {
            initiateAd(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _uuid.value = it.data.result.toString()
                    }
                    is ApiState.Failure -> {
                        _uuid.value = ""
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
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

    private fun getAdType() {
        viewModelScope.launch {
            getAdTypeUseCase(getAccessToken()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _adType.emit(it)

                    }
                    is ApiState.Failure -> {
                        _adType.emit(it)
                    }
                    ApiState.Loading -> {
                    }
                }
            }
        }
    }

    private fun getAccessToken() = token.value

    private fun getUuid() = uuid.value

}