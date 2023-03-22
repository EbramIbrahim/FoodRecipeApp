package com.example.mealz.data.repository

import com.example.mealz.data.remote.MealsApi
import com.example.mealz.domain.models.RandomMealResponse
import com.example.mealz.domain.repository.MealsRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val api: MealsApi
):MealsRepository {

    override suspend fun getRandomMeal(): RandomMealResponse {
       return withContext(Dispatchers.IO){
            api.getRandomMeals()
        }
    }

    override suspend fun getCountryMeals(country: String): RandomMealResponse {
        return withContext(Dispatchers.IO){
            api.getCountriesMeals(country)
        }
    }

    override suspend fun getMealsIngredients(idMeal: String): RandomMealResponse {
        return withContext(Dispatchers.IO){
            api.getMealsIngredients(idMeal)
        }    }
}








