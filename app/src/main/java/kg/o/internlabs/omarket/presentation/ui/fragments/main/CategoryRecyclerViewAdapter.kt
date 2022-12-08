package kg.o.internlabs.omarket.presentation.ui.fragments.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoryRecyclerViewAdapter : RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(kg.o.internlabs.core.R.layout.category_view_holder,parent,false)
        return CategoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 20
    }
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}