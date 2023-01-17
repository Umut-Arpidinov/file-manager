package kg.o.internlabs.omarket.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.o.internlabs.omarket.presentation.ui.fragments.main.adapter.PagingAdapterForMain
import kg.o.internlabs.omarket.presentation.ui.fragments.profile.adapter.AdsPagingAdapter
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI

const val REQUEST_CODE = 100

fun Context.makeToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.makeToast(msg: String) {
    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.getFile(imageUri: Uri): File? {
    val file: File? = imageUri.path?.let { File(it) }
    val filePath = file?.path?.split(":")?.toTypedArray()
    val imageId = filePath?.get(filePath.size - 1)

    val cursor: Cursor? = requireActivity().contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null,
        MediaStore.Images.Media._ID + " = ? ",
        arrayOf(imageId),
        null
    )
    cursor?.moveToFirst()
    val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val result: String? = columnIndex?.let { cursor.getString(it) }
    cursor?.close()
    return result?.let { File(it) }
}

fun Fragment.safeFlowGather(action: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            action()
        }
    }
}

fun Fragment.loadListener(
    adapter: BasePagingAdapter,
    progressBar: ProgressBar,
    recMain: RecyclerView
) {
    val mAdapter = when (adapter) {
        is PagingAdapterForMain -> PagingAdapterForMain()
        else -> AdsPagingAdapter()
    }

    mAdapter.addLoadStateListener { state ->
        recMain.isVisible = state.refresh != LoadState.Loading
        progressBar.isVisible = state.refresh == LoadState.Loading

        val errorState = when {
            state.append is LoadState.Error -> state.append as LoadState.Error
            state.prepend is LoadState.Error -> state.prepend as LoadState.Error
            state.refresh is LoadState.Error -> state.refresh as LoadState.Error
            else -> null
        }
        errorState?.let {
            Toast.makeText(requireActivity(), it.error.toString(), Toast.LENGTH_LONG)
                .show()
        }
    }
}

fun Fragment.requestPermission() {
    //Android is below 11(R)
    ActivityCompat.requestPermissions(
        requireActivity(),
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),
        REQUEST_CODE
    )
}

fun Fragment.checkPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        //Android is 11(R) or above
        Environment.isExternalStorageManager()
    } else {
        //Android is below 11(R)
        val write = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val read = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
    }
}

fun Fragment.glide(fragment: Fragment, uri: URI, imageView: ImageView) {
    Glide.with(fragment).load(uri).centerCrop().into(imageView)
}

fun Fragment.glide(fragment: Fragment, uri: Int, imageView: ImageView) {
    Glide.with(fragment).load(uri).centerCrop().into(imageView)
}

fun glide(fragment: Fragment, uri: String, imageView: ImageView) {
    Glide.with(fragment).load(uri).centerCrop().into(imageView)
}
