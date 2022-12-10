package kg.o.internlabs.omarket.presentation.ui.fragments.main

import  android.content.Context
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

    private var arrayIsNotNull = true

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
            if (arrayIsNotNull) { //check for null list
                Glide.with(context).load(imageURLs?.get(position))
                    .placeholder(R.drawable.loading_animation) //animation during the loading
                    .error(R.drawable.loading_img).into(binding.itemImgMain) //just img if error occurs
            } else {
                Glide.with(context)
                    .load(R.drawable.loading_img) // if list is null set the img
                    .into(binding.itemImgMain)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (imageURLs != null) { // check for null
            imageURLs.size
        } else {
            arrayIsNotNull = false // if null set the one img
            1
        }
    }
}