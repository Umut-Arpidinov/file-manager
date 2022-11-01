package kg.o.internlabs.core

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class CustomSnackbar(private val activity: Activity) {

    fun snackButtonLoading(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_INDEFINITE)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = activity.layoutInflater.inflate(
            R.layout.snackbar_loading, activity.findViewById(R.id.loading_wrapper)
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
            R.layout.snackbar_for_error_and_success, activity.findViewById(R.id.error_and_success_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.snackMessage)
        textMessage.text = text

        val image: ImageView = customView.findViewById(R.id.snackImage)
        image.setImageResource(R.drawable.background_snackbar_success_image_group)

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }

    fun snackButtonError(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = activity.layoutInflater.inflate(
            R.layout.snackbar_for_error_and_success, activity.findViewById(R.id.error_and_success_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.snackMessage)
        textMessage.text = text

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }

    fun snackButtonSystem(text: String, layout: View) {
        val snackBar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val customView = activity.layoutInflater.inflate(
            R.layout.snackbar_system, activity.findViewById(R.id.system_wrapper)
        )

        val textMessage: TextView = customView.findViewById(R.id.textSystem)
        textMessage.text = text

        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }
}