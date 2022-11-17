package kg.o.internlabs.omarket.presentation.ui.activities.activities.main_activity

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.o.internlabs.core.base.BaseViewModel
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.data.remote.model.RegisterDto
import kg.o.internlabs.omarket.domain.entity.RegisterEntity
import kg.o.internlabs.omarket.domain.usecases.RefreshTokenUseCase
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getRefreshTokenPrefsUseCase: GetRefreshTokenPrefsUseCase,
    private val putAccessTokenPrefsUseCase: PutAccessTokenPrefsUseCase,
    private val putRefreshTokenPrefsUseCase: PutRefreshTokenPrefsUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getLoginStatusUseCase: GetLoginStatusUseCase,
    private val setLoginStatusUseCase: SetLoginStatusUseCase
) :
    BaseViewModel() {

    private val _tokens = MutableStateFlow<ApiState<RegisterDto>>(ApiState.Loading)
    private var myJob: Job? = null

    fun statusListener() {
        viewModelScope.launch {
            var status = false
            while (!status) {
                getLoginStatus().take(1).collect {
                    if (it != null) {
                        status = it
                        if (it) {
                            myJob = startRepeatingJob()
                        }
                    }
                }
                delay(1_000)
            }
        }
    }

    private fun startRepeatingJob(timeInterval: Long = 20_000L): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                getRefreshTokenPrefs().take(1).collect {
                    refreshTokenRoute(it).take(1).collect { response ->
                        when (response) {
                            is ApiState.Success -> {
                                println("main         good")
                                response.data.refreshToken?.let { it1 ->
                                    putRefreshTokenPrefsUseCase.invoke(it1)
                                }
                                response.data.accessToken?.let { it1 ->
                                    putAccessTokenPrefsUseCase.invoke(it1)
                                }
                                delay(timeInterval)
                                myJob = startRepeatingJob(1_620_000) // 27 minutes
                            }
                            is ApiState.Failure -> {
                                println("main          fail")
                                _tokens.value = response
                                myJob?.cancel()
                            }
                            ApiState.Loading -> {
                                println("main      loading")
                            }
                        }
                    }
                }
            }
        }
    }

    fun setLoginStatus(isLogged: Boolean) {
        setLoginStatusUseCase.invoke(isLogged)
    }

    private fun getLoginStatus(): Flow<Boolean?> = getLoginStatusUseCase.invoke()

    private fun refreshTokenRoute(it: String?) =
        refreshTokenUseCase(RegisterEntity(refreshToken = it))

    private fun getRefreshTokenPrefs() = getRefreshTokenPrefsUseCase.invoke()

    override fun onCleared() {
        println("onCleared ======")
        setLoginStatusUseCase.invoke(false)
        myJob?.cancel()
        myJob = null
        super.onCleared()
    }
}

