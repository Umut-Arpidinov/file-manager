package kg.o.internlabs.omarket.presentation.ui.fragments.ads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.core.custom_views.cells.Position
import kg.o.internlabs.omarket.databinding.CardOverviewCellBinding

class CellAdapter internal constructor(
    var ad: List<Pair<String, String>>
) :
    RecyclerView.Adapter<CellAdapter.ViewHolder>() {
    class ViewHolder(val binding: CardOverviewCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardOverviewCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mList = ad[position]

        with(holder.binding){
            when(position){
                0 -> cell.setPosition(Position.TOP)
                itemCount - 1 -> cell.setPosition(Position.BOTTOM)
                else -> cell.setPosition(Position.MIDDLE)
            }

            if (mList.second != "" || mList.second.isNotEmpty()) {
                cell.setTitle(mList.first)
                cell.setInfo(mList.second)
            }
        }
    }

    override fun getItemCount(): Int {
        return ad.size
    }
}