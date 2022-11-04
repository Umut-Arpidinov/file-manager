package kg.o.internlabs.core.custom_views

import android.view.MotionEvent


interface OtpResend{
    fun sendOtpAgain()
    fun onTouchDown(event: MotionEvent?, text: String?)
    fun onTouchUp(event: MotionEvent?, text: String?)
}