package kg.o.internlabs.omarket.presentation.ui.fragments.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.GetAccessTokenFromPrefsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val accessTokenPrefsUseCase: GetAccessTokenFromPrefsUseCase,
    private val getAccessTokenFromPrefsUseCase: GetAccessTokenFromPrefsUseCase
    ) :
    BaseViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()
}