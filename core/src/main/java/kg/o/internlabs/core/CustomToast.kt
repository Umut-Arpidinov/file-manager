package kg.o.internlabs.core

import android.app.Activity
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast

//Activity -> MainActivity

class CustomToasts(private val mainActivity: Activity) : Activity() {
    fun toastLoading(text: String) {
        val inflater = mainActivity.layoutInflater
        val layout: View =
            inflater.inflate(R.layout.toast_loading, mainActivity.findViewById(R.id.loading_wrapper))

        Toast(mainActivity).apply {
            val textMessage: TextView = layout.findViewById(R.id.toastLoadingMessage)

            textMessage.text = text
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.CENTER, 0, 0)
            view = layout
        }.show()
    }

    fun toastSuccess(text: String) {
        val inflater = mainActivity.layoutInflater
        val layout: View =
            inflater.inflate(R.layout.toast_success, mainActivity.findViewById(R.id.success_wrapper))

        Toast(mainActivity).apply {
            val textMessage: TextView = layout.findViewById(R.id.toastSuccessMessage)

            textMessage.text = text
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            view = layout
        }.show()
    }

    fun toastError(text: String) {
        val inflater = mainActivity.layoutInflater
        val layout: View =
            inflater.inflate(R.layout.toast_error, mainActivity.findViewById(R.id.error_wrapper))

        Toast(mainActivity).apply {
            val textMessage: TextView = layout.findViewById(R.id.toastErrorMessage)

            textMessage.text = text
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.TOP, 0, 0)
            view = layout
        }.show()
    }

    fun toastTimer(text: String, btnText: String) {
        val inflater = mainActivity.layoutInflater
        val layout: View =
            inflater.inflate(R.layout.toast_timer, mainActivity.findViewById(R.id.timer_wrapper))

        Toast(mainActivity).apply {
            val textMessage: TextView = layout.findViewById(R.id.toastTimerMessage)
            val textBtn: TextView = layout.findViewById(R.id.toastTimerButton)
            val timerText: TextView = layout.findViewById(R.id.toastTimer)

            object : CountDownTimer(5000, 1000) {
                override fun onTick(p0: Long) {
                    timerText.text = "$p0"
                }

                override fun onFinish() {
                    timerText.text = "0"
                }
            }.start()

            textMessage.text = text
            textBtn.text = btnText
            duration = Toast.LENGTH_LONG
            setGravity(Gravity.TOP, 0, 0)
            view = layout
        }.show()
    }
}