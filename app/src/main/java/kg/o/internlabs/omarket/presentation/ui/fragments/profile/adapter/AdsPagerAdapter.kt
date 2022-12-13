package kg.o.internlabs.omarket.presentation.ui.fragments.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.o.internlabs.omarket.databinding.AdapterMovieBinding
import kg.o.internlabs.omarket.domain.entity.MyAdsResultsEntity

class AdsPagerAdapter: PagingDataAdapter<MyAdsResultsEntity, AdsPagerAdapter.AdsViewHolder>
    (AdsComparator) {
    private lateinit var adClicked: AdClicked


    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { getItem(position)?.let {
                it1 -> adClicked.adClicked(it1) } }
    }

    fun setInterface(adClicked: AdClicked) {
        this.adClicked = adClicked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return AdsViewHolder(binding)
    }

    class AdsViewHolder(val view: AdapterMovieBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(item: MyAdsResultsEntity?) = with(view){
            //<editor-fold desc="Инструктция по роуту мой объявление">
            item?.minifyImages // массив ссылок на картинки
            item?.viewCount  // количество просмотров
            item?.price    // цена
            item?.oldPrice   // стараая цена
            item?.currency   // валюта
            item?.title      // название товара
            item?.description  // описание товара
            item?.category?.name    // название категорий
            item?.location       // местоположение или адресс
            item?.category?.delivery  // есть доставка или нет
            //</editor-fold>

            name.text = item?.uuid
            if (item?.minifyImages?.size != 0) {
                Glide.with(root)
                    .load(item?.minifyImages?.get(0))
                    .into(iv)
            }
        }

    }

    object AdsComparator: DiffUtil.ItemCallback<MyAdsResultsEntity>() {
        override fun areItemsTheSame(oldItem: MyAdsResultsEntity, newItem: MyAdsResultsEntity): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: MyAdsResultsEntity, newItem: MyAdsResultsEntity): Boolean {
            return oldItem == newItem
        }
    }
}