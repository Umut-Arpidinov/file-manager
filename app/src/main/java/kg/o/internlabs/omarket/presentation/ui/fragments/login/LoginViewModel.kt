package kg.o.internlabs.omarket.presentation.ui.fragments.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.omarket.data.local.prefs.PrefsRepositoryImpl
import kg.o.internlabs.omarket.domain.usecases.CheckNumberPrefs
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: PrefsRepositoryImpl
) : BaseViewModel() {

    private val checkNumberPrefs = CheckNumberPrefs(repository)

}