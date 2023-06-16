package com.example.hammertest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hammertest.R
import com.example.hammertest.databinding.DisheItemBinding
import com.example.hammertest.models.Dishe
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.squareup.picasso.Picasso

//адаптер для блюд
class DisheAdapterDelegate(private val onItemClick: (Dishe) -> Unit) : AdapterDelegate<List<Any>>() {
    //заполняем элементы и ставим слушатель нажатий
    private inner class DishesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DisheItemBinding.bind(itemView)
        fun bind(item: Dishe) = with(binding) {
            tvDisheName.text = item.name
            Picasso.get().load(item.image_url).into(imDishe)
            val price = "от " +  item.price.toString() + " р"
            tvPrice.text = price
            tvDescription.text = item.description
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return items[position] is Dishe
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dishe_item, parent, false)
        return DishesViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val dish = items[position] as Dishe
        val dishViewHolder = holder as DishesViewHolder
        dishViewHolder.bind(dish)
    }
}