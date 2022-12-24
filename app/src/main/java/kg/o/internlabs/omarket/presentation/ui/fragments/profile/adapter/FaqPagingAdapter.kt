package kg.o.internlabs.omarket.presentation.ui.fragments.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.databinding.ItemFaqBinding
import kg.o.internlabs.omarket.domain.entity.ResultsEntity
import kg.o.internlabs.omarket.utils.BasePagingAdapter

class FaqPagingAdapter: PagingDataAdapter<ResultsEntity, FaqPagingAdapter.FaqViewHolder>
    (FaqComparator), BasePagingAdapter {

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFaqBinding.inflate(inflater, parent, false)
        return FaqViewHolder(binding)
    }

    inner class FaqViewHolder(val binding: ItemFaqBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultsEntity?) = with(binding) {
            itemFaq1.setQuestions(item?.title.toString())
            itemFaq1.setAnswers(item?.content.toString())
        }
    }

    object FaqComparator : DiffUtil.ItemCallback<ResultsEntity>() {
        override fun areItemsTheSame(
            oldItem: ResultsEntity,
            newItem: ResultsEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultsEntity,
            newItem: ResultsEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}