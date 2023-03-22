package com.example.mealz.domain.usecase

import com.example.mealz.data.repository.MealsRepositoryImpl
import com.example.mealz.domain.models.RandomMealResponse
import com.example.mealz.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CountryMealsUseCase @Inject constructor(
    private val repository: MealsRepositoryImpl
) {

    operator fun invoke(country: String): Flow<Resource<RandomMealResponse>> = flow {
        try {
            emit(Resource.Loading())
            delay(3000L)

            val result = repository.getCountryMeals(country)
            emit(Resource.Success(result))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    "Can't reach the server....Please check your internet connection"
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    "Something wrong occurred.."
                )
            )
        }
    }

}