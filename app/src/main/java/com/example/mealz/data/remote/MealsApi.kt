package com.example.mealz.data.remote

import com.example.mealz.domain.models.RandomMealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {


    @GET("random.php")
    suspend fun getRandomMeals(): RandomMealResponse


    @GET("filter.php?")
    suspend fun getCountriesMeals(
        @Query("a") country: String
    ): RandomMealResponse


    @GET("lookup.php?")
    suspend fun getMealsIngredients(
        @Query("i") idMeal: String
    ): RandomMealResponse


}






