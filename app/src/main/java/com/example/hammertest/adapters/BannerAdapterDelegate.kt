package com.example.hammertest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hammertest.R
import com.example.hammertest.databinding.BannerItemBinding
import com.example.hammertest.models.Category
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.squareup.picasso.Picasso

//адаптер для баннеров
class BannerAdapterDelegate(private val onItemClick: (Category) -> Unit) : AdapterDelegate<List<Any>>() {

    private inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = BannerItemBinding.bind(itemView)
        fun bind(item: Category) = with(binding) {
            Picasso.get().load(item.image_url).into(imBanner)
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return items[position] is Category
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val banner = items[position] as Category
        val viewHolder = holder as BannerViewHolder
        viewHolder.bind(banner)
    }
}