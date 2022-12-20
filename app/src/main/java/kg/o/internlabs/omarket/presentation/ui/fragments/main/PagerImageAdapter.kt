package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.o.internlabs.omarket.R
import kg.o.internlabs.omarket.databinding.PagerItemImageMainBinding

internal class PagerImageAdapter internal constructor(
    private val context: Context,
    private val imageURLs: List<String>?,
    private val itemWidth: Int
) :
    RecyclerView.Adapter<PagerImageAdapter.ViewHolder>() {

    internal class ViewHolder(val binding: PagerItemImageMainBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PagerItemImageMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.itemImgMain.layoutParams.width = itemWidth

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            if (!imageURLs.isNullOrEmpty()) {
                Glide.with(context).load(imageURLs[position])
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.loading_img).into(binding.itemImgMain)
            } else {
                Glide.with(context)
                    .load(R.drawable.loading_img)
                    .into(binding.itemImgMain)
            }
        }
    }

    override fun getItemCount(): Int {
        return imageURLs?.size ?: 0
    }
}