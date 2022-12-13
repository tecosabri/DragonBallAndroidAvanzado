package com.isabri.dragonballandroidavanzado.ui.commons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.isabri.dragonballandroidavanzado.R
import com.isabri.dragonballandroidavanzado.databinding.ItemListBinding
import com.isabri.dragonballandroidavanzado.domain.models.Hero

class HeroesListAdapter(private val clickListener: (Hero) -> (Unit)) : ListAdapter<Hero, HeroesListAdapter.HeroViewHolder>(HeroDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        val binding = ItemListBinding.bind(view)
        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class HeroViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var hero: Hero

        init {
            binding.root.setOnClickListener {
                clickListener(hero)
            }
        }

        fun bind(hero: Hero) {
            this.hero = hero
            with(binding) {
                tvHeroName.text = hero.name
                ivHeroImage.load(hero.photo)
            }
        }
    }

    class HeroDiffCallback : DiffUtil.ItemCallback<Hero>() {
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem == newItem
        }
    }
}