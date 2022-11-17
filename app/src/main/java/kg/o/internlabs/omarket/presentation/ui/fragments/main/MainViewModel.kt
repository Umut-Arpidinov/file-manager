package kg.o.internlabs.omarket.presentation.ui.fragments.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenPrefsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accessTokenPrefsUseCase: GetAccessTokenPrefsUseCase
    ) :
    BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()
}