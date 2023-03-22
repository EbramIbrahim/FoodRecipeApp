package com.example.mealz.presentation.di

import com.example.mealz.domain.repository.MealsRepository
import com.example.mealz.domain.usecase.AllUseCases
import com.example.mealz.domain.usecase.CountryMealsUseCase
import com.example.mealz.domain.usecase.GetMealIngredientsUseCase
import com.example.mealz.domain.usecase.RandomMealUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Singleton
    @Provides
    fun provideUseCase(
        randomMealUseCase: RandomMealUseCase,
        countryMealsUseCase: CountryMealsUseCase,
        mealIngredientsUseCase: GetMealIngredientsUseCase
    ):AllUseCases {
        return AllUseCases(
            randomMealUseCase = randomMealUseCase,
            countryMealsUseCase = countryMealsUseCase,
            mealIngredients = mealIngredientsUseCase
        )
    }








}