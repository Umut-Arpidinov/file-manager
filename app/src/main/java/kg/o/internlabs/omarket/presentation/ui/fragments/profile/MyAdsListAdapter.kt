package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.data.remote.model.ResultX
import kg.o.internlabs.omarket.databinding.CardViewUsersAdsBinding

class MyAdsListAdapter internal constructor(
    var list: List<ResultX>,
    private val itemWidth: Int,
    val context: Context
) :
    RecyclerView.Adapter<MyAdsListAdapter.ViewHolder>()
{
    class ViewHolder(val binding: CardViewUsersAdsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdsListAdapter.ViewHolder {
        val binding =
            CardViewUsersAdsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.cardView.layoutParams.width = itemWidth

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }
}