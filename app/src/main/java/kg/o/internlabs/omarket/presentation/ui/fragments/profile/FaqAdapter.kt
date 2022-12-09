package kg.o.internlabs.omarket.presentation.ui.fragments.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.o.internlabs.omarket.databinding.ItemFaqBinding
import kg.o.internlabs.omarket.domain.entity.ResultsEntity

class FaqAdapter(private val faq: ArrayList<ResultsEntity> = ArrayList()) : RecyclerView.Adapter<FaqAdapter.Holder>() {



    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFaqBinding.bind(itemView)
        fun bind(faqEntity: ResultsEntity) = with(binding){
            itemFaq1.setQuestions(faqEntity.id.toString())
            itemFaq1.setQuestions(faqEntity.title.toString())
            itemFaq1.setQuestions(faqEntity.content.toString())
            itemFaq1.setAnswers(faqEntity.id.toString())
            itemFaq1.setAnswers(faqEntity.title.toString())
            itemFaq1.setAnswers(faqEntity.content.toString())
            println("----------------------"+itemFaq1.setQuestions(faqEntity.title.toString()))


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(kg.o.internlabs.omarket.R.layout.item_faq ,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(faq[position])


    }

    override fun getItemCount(): Int {

      return faq.size
    }
}