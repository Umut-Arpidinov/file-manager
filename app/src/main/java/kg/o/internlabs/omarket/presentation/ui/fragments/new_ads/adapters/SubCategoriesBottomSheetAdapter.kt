package kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.core.custom_views.cells.Position
import kg.o.internlabs.omarket.databinding.RvCellItemBinding
import kg.o.internlabs.omarket.domain.entity.SubCategoriesEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.SubCategoryClickHandler

class SubCategoriesBottomSheetAdapter(
    private var list: List<SubCategoriesEntity>?,
    private val clickHandler: SubCategoryClickHandler
) : RecyclerView.Adapter<SubCategoriesBottomSheetAdapter.SubCategoriesBottomSheetViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoriesBottomSheetViewHolder {
        val binding = RvCellItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SubCategoriesBottomSheetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCategoriesBottomSheetViewHolder, position: Int) {
        holder.bind(list?.get(position), position)
        holder.itemView.setOnClickListener {
            clickHandler.subClickHandler(list?.get(position))
        }
    }

    override fun getItemCount(): Int = list!!.size

    inner class SubCategoriesBottomSheetViewHolder(val binding: RvCellItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @Suppress("KotlinConstantConditions")
        fun bind(item: SubCategoriesEntity?, position: Int) = with(binding) {
            cell.setTitle(item?.name.toString())
            when (position) {
                0 -> cell.setPosition(Position.TOP)
                list?.lastIndex -> cell.setPosition(Position.BOTTOM)
                else -> cell.setPosition(Position.MIDDLE)
            }
        }
    }
}