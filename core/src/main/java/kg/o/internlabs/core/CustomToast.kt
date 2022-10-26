package kg.o.internlabs.core

import android.app.Activity
import android.graphics.Color
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

//Activity -> MainActivity

class CustomToasts(private val mainActivity: Activity) : Activity() {

    fun snackButtonLoading(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = mainActivity.layoutInflater.inflate(
            R.layout.toast_loading, mainActivity.findViewById(R.id.loading_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.toastLoadingMessage)
        textMessage.text = text

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }

    fun snackButtonSuccess(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = mainActivity.layoutInflater.inflate(
            R.layout.toast_success, mainActivity.findViewById(R.id.success_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.toastSuccessMessage)
        textMessage.text = text

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }

    fun snackButtonError(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = mainActivity.layoutInflater.inflate(
            R.layout.toast_error, mainActivity.findViewById(R.id.error_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.toastErrorMessage)
        textMessage.text = text

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }

    fun snackButtonTimer(text: String, btnText: String, layout: View, timerStart: Long, timerEnd: Long) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_INDEFINITE)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = mainActivity.layoutInflater.inflate(
            R.layout.toast_timer, mainActivity.findViewById(R.id.timer_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.toastTimerMessage)
        val timerText: TextView = customView.findViewById(R.id.toastTimer)
        val button: Button = customView.findViewById(R.id.toastTimerButton)

        object : CountDownTimer(timerStart, timerEnd) {
            override fun onTick(p0: Long) {
                timerText.text = "$p0"
            }

            override fun onFinish() {
                timerText.text = "0"
                snackBarLayout.removeAllViews()
            }
        }.start()

        button.setOnClickListener {
            Toast.makeText(mainActivity, "Logic to be done.", Toast.LENGTH_SHORT).show()
            snackBar.dismiss()
            snackBarLayout.removeAllViews()
        }

        textMessage.text = text
        button.text = btnText

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }
}