package kg.o.internlabs.omarket.presentation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.domain.entity.CategoriesEntity
import kg.o.internlabs.omarket.domain.usecases.GetCategoriesUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
    ) :
    BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _categories = MutableSharedFlow<ApiState<CategoriesEntity>>()
    val categories = _categories.asSharedFlow()

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