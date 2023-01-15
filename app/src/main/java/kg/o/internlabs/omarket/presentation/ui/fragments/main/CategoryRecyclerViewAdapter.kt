package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CategoryViewHolderBinding
import kg.o.internlabs.omarket.domain.entity.ResultEntity

class CategoryRecyclerViewAdapter(
    private var listOfResult: List<ResultEntity>?,
    private val context: Context,
    private val clickHandler: CategoryClickHandler

    ) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryViewHolderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(listOfResult?.get(position), context)
        holder.itemView.setOnClickListener {
            clickHandler.clickedCategory(listOfResult?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return listOfResult!!.size
    }

    inner class CategoryViewHolder(val binding: CategoryViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultEntity?, context: Context) = with(binding) {
                categoryName.text = item?.name
                if (item?.name == "Все") {
                    Glide.with(context)
                        .load(R.drawable.category_all_union)
                        .into(categoryImage)
                } else {
                    Glide.with(context)
                        .load(item?.iconImg)
                        .fitCenter()
                        .into(categoryImage)
                }
        }
    }
}
