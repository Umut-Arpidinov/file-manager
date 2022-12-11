package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.databinding.ItemFaqBinding
import kg.o.internlabs.omarket.domain.entity.ResultsEntity

class FaqAdapter(private val results: List<ResultsEntity>) :
    RecyclerView.Adapter<FaqAdapter.Holder>() {

    class Holder(val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(results: ResultsEntity) = with(binding) {
            itemFaq1.setQuestions(results.title.toString())
            itemFaq1.setAnswers(results.content.toString())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding = ItemFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int = results.size

}