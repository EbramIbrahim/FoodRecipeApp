package com.example.mealz.domain.usecase

data class AllUseCases(
    val randomMealUseCase: RandomMealUseCase,
    val countryMealsUseCase: CountryMealsUseCase,
    val mealIngredients: GetMealIngredientsUseCase
)
