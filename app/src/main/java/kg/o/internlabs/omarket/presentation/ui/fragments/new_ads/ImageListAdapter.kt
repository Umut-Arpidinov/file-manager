package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.o.internlabs.core.common.ApiState
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.ImageItemBinding
import kg.o.internlabs.omarket.domain.entity.UploadImageResultEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.LoadImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.MainImageSelectHelper
import kg.o.internlabs.omarket.utils.safeFlowGather
import kotlinx.coroutines.flow.collectLatest

class ImageListAdapter(
    private var context: Context,
    private val clickers: NewAdsFragment,
    private val viewModel: NewAdsViewModel
) : RecyclerView.Adapter<ImageListAdapter.ImageHolder>() {

    private var selectedImages = mutableListOf<UploadImageResultEntity>()
    private var list: List<UploadImageResultEntity> = arrayListOf()
    private var mainImageIndex: Int = 1
    private var loadedImageIndex: Int = -1
    private var selectHelper: MainImageSelectHelper? = null
    private var loadImage: LoadImageHelper? = null
    private var deleteImage: DeleteImageHelper? = null

    @SuppressLint("NotifyDataSetChanged")
    fun initAdapter(
        list: List<UploadImageResultEntity>
    ) {
        this.list = list
        with(clickers) {
            selectHelper = this
            loadImage = this
            deleteImage = this
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        println("---444----" + list)
        holder.bind(list[position], position)
        holder.itemView.setOnClickListener {
            println("----00---" + list[position])
            selectHelper?.selectMainImage(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectMainImage(index: Int) {
        mainImageIndex = index
        notifyDataSetChanged()
    }

    inner class ImageHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: UploadImageResultEntity, position: Int) = with(binding) {
            println("---666___" + uri)
           /* if (position == mainImageIndex) {
                flAddImage.isVisible = list.isNotEmpty()
                flLoadImage.isVisible = flAddImage.isVisible.not()
            } else {
                flLoadImage.isVisible = true
                flAddImage.isVisible = false*/

            flLoadImage.isVisible = position == loadedImageIndex
            progressBar.isVisible = flLoadImage.isVisible.not()
            println("/00./....$position.....$loadedImageIndex.........${uri.path}.....................")
            if (flLoadImage.isVisible) {
                println("/./....$position.....$loadedImageIndex.........${uri.path}.....................")
                Glide.with(root).load(uri.path).into(ivLoadImage)
            }
                selectedImage(position)
                cvLoadImage.isVisible = false
                //}

        }

        private fun selectedImage(position: Int) = with(binding) {
            println("--777--${position}")
            if (position == mainImageIndex) {
                flLoadImage.background = context.let {
                    AppCompatResources.getDrawable(
                        it, R.drawable.bg_main_image
                    )
                }
                ivLoadImage.imageAlpha = 150
                tvMainImage.isVisible = true
            } else {
                flLoadImage.background = context.let {
                    AppCompatResources.getDrawable(
                        it, R.drawable.bg_image_load
                    )
                }
                ivLoadImage.imageAlpha = 255
                cvLoadImage.setCardBackgroundColor(Color.WHITE)
                tvMainImage.isVisible = false
            }
        }

        fun getUploadedImage() {
            clickers.safeFlowGather {
                viewModel.uploadImage.collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            it.data.result?.let { it1 -> addIfNotContains(it1) }
                        }
                        is ApiState.Failure -> {
                            println("--....1.." + it.msg.message)
                        }
                        is ApiState.Loading -> {
                            println("--....2..Loading")
                            /*btnNonActive.isVisible = false
                        btnActive.isVisible = false*/
                        }
                    }
                    println("here..................")
                }
            }
        }
    }

    private fun addIfNotContains(it1: UploadImageResultEntity) {
        if (selectedImages.contains(it1)) return
        selectedImages.add(0, it1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun imageLoaded(index: Int) {
        loadedImageIndex = index
        notifyDataSetChanged()
    }
}