package kg.o.internlabs.omarket.utils

import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat

fun lightStatusBar(window: Window,lightIcons: Boolean) {
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    if (lightIcons){
        wic.isAppearanceLightStatusBars
    } else {
        wic.isAppearanceLightStatusBars
    }
}