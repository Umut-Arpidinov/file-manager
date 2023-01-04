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
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.ImageItemBinding
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.DeleteImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.LoadImageHelper
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.MainImageSelectHelper

class ImageListAdapter(private var context: Context, private val clickers: NewAdsFragment)
    : RecyclerView.Adapter<ImageListAdapter.ImageHolder>() {

    private var list: List<String> = arrayListOf()
    private var mainImageIndex: Int = 1
    private var selectHelper: MainImageSelectHelper? = null
    private var loadImage: LoadImageHelper? = null
    private var deleteImage: DeleteImageHelper? = null

    @SuppressLint("NotifyDataSetChanged")
    fun initAdapter(
        list: List<String>
    ) {
        this.list = list
        selectHelper = clickers
        loadImage = clickers
        deleteImage = clickers
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

        fun bind(uri: String, position: Int) = with(binding) {
            println("---666___" + uri)
            if (position == 0) {
                flAddImage.isVisible = list.isNotEmpty()
                flLoadImage.isVisible = flAddImage.isVisible.not()
            } else {
                flLoadImage.isVisible = true
                flAddImage.isVisible = false
                selectedImage(position)
                Glide.with(binding.root).load(uri).into(ivLoadImage)
            }
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
    }
}