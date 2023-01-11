package kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.AddImageItemBinding
import kg.o.internlabs.omarket.databinding.LoadedImageItemBinding
import kg.o.internlabs.omarket.databinding.LoadingImageItemBinding
import kg.o.internlabs.omarket.databinding.NoItemBinding
import kg.o.internlabs.omarket.domain.entity.UploadImageResultEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.AddImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.helpers.MainImageSelectHelper

private const val ADD_STATE = 0
private const val LOADING_STATE = 1
private const val LOADED_STATE = 2
private const val INVALID_STATE = -1

internal class ImageListAdapter(
    private val clickers: EditAdsFragment,
) : RecyclerView.Adapter<ImageListAdapter.ImageHolder>() {

    private var list: List<UploadImageResultEntity> = arrayListOf()
    private var mainImageIndex: Int = 1
    private var selectMain: MainImageSelectHelper? = null
    private var addImage: AddImageHelper? = null
    private var deleteImage: DeleteImageHelper? = null

    @SuppressLint("NotifyDataSetChanged")
    fun initAdapter(
        list: List<UploadImageResultEntity>
    ) {
        this.list = list
        with(clickers) {
            selectMain = this
            addImage = this
            deleteImage = this
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = when (viewType) {
            ADD_STATE -> {
                AddImageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            }
            LOADING_STATE -> {
                LoadingImageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            }
            LOADED_STATE -> {
                LoadedImageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            }
            else -> {
                NoItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            }
        }
        return ImageHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            if (list.size >= 11) return INVALID_STATE
            return ADD_STATE
        }
        return when (list[position].url == null) {
            true -> LOADING_STATE
            else -> LOADED_STATE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectMainImage(index: Int) {
        mainImageIndex = index
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun imageLoaded(updatedList: MutableList<UploadImageResultEntity>) {
        list = updatedList
        notifyDataSetChanged()
    }

    internal inner class ImageHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding.root) {
                when (binding) {
                    is AddImageItemBinding -> {
                        setOnClickListener {
                            addImage?.addImage()
                        }
                    }
                    is LoadedImageItemBinding -> {
                        findViewById<ImageView>(R.id.iv_delete).setOnClickListener {
                            deleteImage?.deleteImage(absoluteAdapterPosition)
                        }
                        setOnClickListener {
                            selectMain?.selectMainImage(absoluteAdapterPosition)
                        }
                    }
                }
            }
        }

        fun bind(uri: UploadImageResultEntity, position: Int) = with(binding) {
            when (binding) {
                is AddImageItemBinding -> {
                    if (list.size >= 11) {
                        root.visibility = View.GONE
                    }
                }
                is LoadedImageItemBinding -> {
                    Glide.with(root).load(uri.path)
                        .into(binding.root.findViewById(R.id.iv_load_image))

                    selectedImage(position)
                }
            }
        }

        private fun selectedImage(position: Int) = with(binding.root) {
            if (position == mainImageIndex) {
                background = context.let {
                    AppCompatResources.getDrawable(
                        it, R.drawable.bg_main_image
                    )
                }
                findViewById<ImageView>(R.id.iv_load_image)
                    .setColorFilter(Color.rgb(100, 100, 100), PorterDuff.Mode.MULTIPLY)
                findViewById<TextView>(R.id.tv_main_image).isVisible = true
            } else {
                findViewById<FrameLayout>(R.id.fl_loaded_image).background = context.let {
                    AppCompatResources.getDrawable(
                        it, R.drawable.bg_image_load
                    )
                }
                findViewById<ImageView>(R.id.iv_load_image).clearColorFilter()
                findViewById<TextView>(R.id.tv_main_image).isVisible = false
            }
        }
    }
}