package com.example.mealz.presentation.home_screen

import com.example.mealz.domain.models.RandomMealResponse


data class RandomMealState(

    val randomMeal: RandomMealResponse? = null,
    val error: String? = null,
    val loading: Boolean = false

)
