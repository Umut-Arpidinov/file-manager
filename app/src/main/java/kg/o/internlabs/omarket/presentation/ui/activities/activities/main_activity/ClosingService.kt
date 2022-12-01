package kg.o.internlabs.omarket.presentation.ui.activities.activities.main_activity

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kg.o.internlabs.omarket.domain.usecases.shared_prefs_use_cases.SaveLoginStatusToPrefsUseCase
import javax.annotation.Nullable

class ClosingService : Service() {
    private val saveLoginStatusToPrefsUseCase: SaveLoginStatusToPrefsUseCase? = null

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        println("----------onDest")
        saveLoginStatusToPrefsUseCase?.invoke(false)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("----onStartCommand")
        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        println("--------service closed")
        saveLoginStatusToPrefsUseCase?.invoke(false)
        stopSelf()
    }
}