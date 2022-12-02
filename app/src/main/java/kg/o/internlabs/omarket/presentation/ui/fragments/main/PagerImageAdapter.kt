package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.databinding.PagerItemImageMainBinding

//#TODO(Make a RecyclerAdapter for main page and complete adapter that depends on INPUT)

internal class PagerImageAdapter internal constructor(
    private var count: Int,
    private val itemWidth: Int
) :
    RecyclerView.Adapter<PagerImageAdapter.ViewHolder>() {

    internal class ViewHolder(val binding: PagerItemImageMainBinding) : RecyclerView.ViewHolder(binding.root)

    fun setCount(count: Int) {
        this.count = count
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PagerItemImageMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.itemImgMain.layoutParams.width = itemWidth

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.itemImgMain.setImageResource(kg.o.internlabs.core.R.drawable.img_sample)
        }
    }

    override fun getItemCount(): Int {
        return count
    }
}