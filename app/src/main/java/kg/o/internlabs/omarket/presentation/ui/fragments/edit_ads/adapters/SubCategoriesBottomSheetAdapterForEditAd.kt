package kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.core.custom_views.cells.Position
import kg.o.internlabs.omarket.databinding.RvCellItemBinding
import kg.o.internlabs.omarket.domain.entity.SubCategoriesEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.new_ads.helpers.SubCategoryClickHandler

class SubCategoriesBottomSheetAdapterForEditAd(
    private var list: List<SubCategoriesEntity>?,
    private val clickHandler: SubCategoryClickHandler
) : RecyclerView.Adapter<SubCategoriesBottomSheetAdapterForEditAd.VH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val binding = RvCellItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list?.get(position), position)
        holder.itemView.setOnClickListener {
            clickHandler.subClickHandler(list?.get(position))
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class VH(val binding: RvCellItemBinding) :
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