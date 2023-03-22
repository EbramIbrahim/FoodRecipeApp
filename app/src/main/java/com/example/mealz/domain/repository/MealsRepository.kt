package com.example.mealz.domain.repository

import com.example.mealz.domain.models.RandomMealResponse


interface MealsRepository {

    suspend fun getRandomMeal(): RandomMealResponse
    suspend fun getCountryMeals(country: String): RandomMealResponse
    suspend fun getMealsIngredients(idMeal: String): RandomMealResponse


}