package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.R

//#TODO(Make a RecyclerAdapter for main page and complete adapter that depends on INPUT)

internal class PagerImageAdapter internal constructor(
    private var count: Int,
    private val itemWidth: Int
) :
    RecyclerView.Adapter<PagerImageAdapter.ViewHolder>() {

    fun setCount(count: Int) {
        this.count = count
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item_image_main, parent, false)
        view.layoutParams.width = itemWidth
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setImageResource(kg.o.internlabs.core.R.drawable.img_sample)
    }

    override fun getItemCount(): Int {
        return count
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: ImageView

        init {
            title = itemView.findViewById(R.id.item_img_main)
        }
    }
}