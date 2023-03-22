package com.example.mealz.presentation.di

import com.example.mealz.data.remote.MealsApi
import com.example.mealz.data.repository.MealsRepositoryImpl
import com.example.mealz.domain.repository.MealsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideMealsRepository(api: MealsApi): MealsRepository {
        return MealsRepositoryImpl(api)
    }




}