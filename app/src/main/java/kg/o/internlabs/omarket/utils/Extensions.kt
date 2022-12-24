package kg.o.internlabs.omarket.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
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
import java.net.URI

fun Context.makeToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.makeToast(msg: String) {
    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
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

fun Fragment.checkPermission() {
    if (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        != PackageManager.PERMISSION_GRANTED
    ) {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(
                "package:${requireActivity().packageName}"
            )
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
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
