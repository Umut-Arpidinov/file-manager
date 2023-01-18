package kg.o.internlabs.omarket.presentation.ui.fragments.detailAd.adapter

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.PagerItemImageOverviewBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

internal class DetailedImageAdapter internal constructor(
    private val context: Context,
    private val imageURLs: List<String>?,
    private val itemWidth: Int,
    private val viewer: Boolean,
    private val activity: FragmentActivity?
) : RecyclerView.Adapter<DetailedImageAdapter.ViewHolder>() {
    lateinit var imageClicked: ImageClickedAds
    private var isEmpty = false

    internal class ViewHolder(val binding: PagerItemImageOverviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PagerItemImageOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.itemImgMain.layoutParams.width = itemWidth

        if (viewer) {
            binding.itemImgMain.layoutParams.height = LayoutParams.WRAP_CONTENT
            binding.itemImgMain.scaleType = ImageView.ScaleType.CENTER
        } else {
            binding.itemImgMain.layoutParams.height = LayoutParams.MATCH_PARENT
            binding.itemImgMain.scaleType = ImageView.ScaleType.CENTER_CROP
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            if (isEmpty) {
                Glide.with(context).load(R.drawable.ic_img_empty).into(binding.itemImgMain)
            } else {
                Glide.with(context).load(imageURLs?.get(position))
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_img_empty).into(binding.itemImgMain)
            }

            if (!viewer) {
                itemView.setOnClickListener {
                    itemView.let {
//                        imageClicked.imageClicked()
                        return@setOnClickListener
                    }
                }
            } else {
                itemView.setOnClickListener {
                    val bitmap = getImageOfView(binding.itemImgMain)
                    saveImage(bitmap)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (imageURLs?.isNotEmpty() == true) {
            isEmpty = false
            return imageURLs.size
        } else
            isEmpty = true
        return 1
    }

    fun setInterface(imageClicked: ImageClickedAds) {
        this.imageClicked = imageClicked
    }

    fun getImageOfView(itemView: ImageView): Bitmap? {
        var image: Bitmap? = null
        try {
            image = Bitmap.createBitmap(itemView.measuredWidth, itemView.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(image)
            itemView.draw(canvas)
        } catch (e: Exception) {
            Toast.makeText(context, "Can't save Image", Toast.LENGTH_SHORT).show()
        }

        return image
    }

    fun saveImage(bitmap: Bitmap?) {
        var fos: OutputStream? = null
        val imageName = "noob_${System.currentTimeMillis()}.jpg"

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
            activity?.contentResolver?.also { contentResolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let {
                    contentResolver.openOutputStream(it)
                }
            }
        } else {
            val imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imageDirectory, imageName)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}