package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.DeleteImageEntity
import kg.o.internlabs.omarket.domain.entity.EditAds
import kg.o.internlabs.omarket.domain.entity.UploadImageEntity
import kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases.DeleteImageFromAdUseCase
import kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases.EditAnAdUseCase
import kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases.InitiateAdUseCase
import kg.o.internlabs.omarket.domain.usecases.crud_ad_usecases.UploadImageToAdUseCase
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
    private val editAnAdUseCase: EditAnAdUseCase
) : BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _uuid = MutableStateFlow("")
    val uuid = _uuid.asStateFlow()

    private val _uploadImage = MutableSharedFlow<ApiState<UploadImageEntity>>()
    val uploadImage = _uploadImage.asSharedFlow()

    private val _editedAd = MutableSharedFlow<ApiState<EditAds>>()
    val editedAd = _editedAd.asSharedFlow()

    private val _deleteImage = MutableSharedFlow<ApiState<DeleteImageEntity>>()
    val deleteImage = _deleteImage.asSharedFlow()

    init {
        getAccessTokenFromPrefs()
    }

    fun initViewModel() {}

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

    fun uploadImage(uri: File?) {
        println("><><><>< $uuid")
        viewModelScope.launch {
            uploadImageToAdUseCase(getAccessToken(), uri, getUuid())
                .collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        println("===============================")
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

    fun deleteAvatar(uuid: String) {
        viewModelScope.launch {
            deleteImageFromAdUseCase(getAccessToken(), uuid).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        _deleteImage.emit(it)
                    }
                    is ApiState.Failure -> {
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

    private fun getAccessToken() = token.value

    private fun getUuid() = uuid.value

}