package kg.o.internlabs.omarket.presentation.ui.fragments.edit_ads.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.core.custom_views.cells.Position
import kg.o.internlabs.omarket.databinding.CategoryItemBinding
import kg.o.internlabs.omarket.domain.entity.ResultEntity
import kg.o.internlabs.omarket.presentation.ui.fragments.main.CategoryClickHandler

class CategoriesBottomSheetAdapterForEditAd(
    private var list: List<ResultEntity>?,
    private val clickHandler: CategoryClickHandler
) :
    RecyclerView.Adapter<CategoriesBottomSheetAdapterForEditAd.VH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val binding = CategoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list?.get(position), position)
        holder.itemView.setOnClickListener {
            clickHandler.clickedCategory(list?.get(position))
        }
    }

    override fun getItemCount(): Int = list!!.size

    inner class VH(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @Suppress("KotlinConstantConditions")
        fun bind(item: ResultEntity?, position: Int) = with(binding) {
            cell.setTitle(item?.name.toString())
            cell.setIcon(item?.iconImg.toString())
            when(position) {
                0 -> cell.setPosition(Position.TOP)
                list?.lastIndex -> cell.setPosition(Position.BOTTOM)
                else -> cell.setPosition(Position.MIDDLE)
            }
        }
    }
}