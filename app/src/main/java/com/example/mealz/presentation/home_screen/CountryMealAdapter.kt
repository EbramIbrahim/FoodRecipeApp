package com.example.mealz.presentation.home_screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mealz.databinding.CountryMealsItemsBinding
import com.example.mealz.domain.models.Meal

class CountryMealAdapter : RecyclerView.Adapter<CountryMealAdapter.MealsViewHolder>() {

    private val countryMealList = mutableListOf<Meal>()
    private lateinit var onItemClick: OnItemClick

    inner class MealsViewHolder(private val binding: CountryMealsItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(countryMeals: Meal) {
            Glide.with(itemView)
                .load(countryMeals.strMealThumb)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.countryMealIm)

            binding.countryMealTv.text = countryMeals.strMeal

            itemView.setOnClickListener {
                onItemClick.onItemClick(countryMeals)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val binding = CountryMealsItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        val countryMeal = countryMealList[position]
        holder.bind(countryMeal)

    }

    override fun getItemCount(): Int {
        return countryMealList.size
    }

    fun setOnClickListener(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(countryMealsList: List<Meal>) {
        this.countryMealList.clear()
        this.countryMealList.addAll(countryMealsList)
        notifyDataSetChanged()
    }

    interface OnItemClick {
        fun onItemClick(meal: Meal)
    }


}