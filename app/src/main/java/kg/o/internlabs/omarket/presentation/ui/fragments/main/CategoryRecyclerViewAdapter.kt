package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.o.internlabs.core.R
import kg.o.internlabs.core.databinding.CategoryViewHolderBinding
import kg.o.internlabs.omarket.domain.entity.ResultEntity


class CategoryRecyclerViewAdapter(
    private var listOfResult: List<ResultEntity>?,
    private val context: Context,
    private val listener: OnItemClickListener

) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryViewHolderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(listOfResult?.get(position), context)
    }

    override fun getItemCount(): Int {
        return listOfResult!!.size
    }


    class CategoryViewHolder(val binding: CategoryViewHolderBinding,val itemListener:OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
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
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                itemListener.onItemClick(position)
            }

        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

}
