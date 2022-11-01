package kg.o.internlabs.core

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class CustomToasts(private val activity: Activity) {

    fun snackButtonLoading(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_INDEFINITE)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = activity.layoutInflater.inflate(
            R.layout.toast_loading, activity.findViewById(R.id.loading_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.toastLoadingMessage)
        textMessage.text = text

        snackBar.view.setOnClickListener {
            snackBar.dismiss()
            snackBarLayout.removeAllViews()
        }

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }

    fun snackButtonSuccess(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = activity.layoutInflater.inflate(
            R.layout.toast_success, activity.findViewById(R.id.success_wrapper)
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

        val customView = activity.layoutInflater.inflate(
            R.layout.toast_error, activity.findViewById(R.id.error_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.toastErrorMessage)
        textMessage.text = text

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }

    fun snackButtonSystem(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = activity.layoutInflater.inflate(
            R.layout.toast_system, activity.findViewById(R.id.system_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.textSystem)
        textMessage.text = text

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }
}