package com.example.mealz.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealz.domain.usecase.AllUseCases
import com.example.mealz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: AllUseCases
) : ViewModel() {


    private val _randomMealsState: MutableStateFlow<RandomMealState?> =
        MutableStateFlow(null)

    val randomMealState = _randomMealsState.asStateFlow()

    private val _countryMealsState: MutableStateFlow<RandomMealState?> =
        MutableStateFlow(null)

    val countryMealState = _countryMealsState.asStateFlow()

    val query: MutableStateFlow<String> = MutableStateFlow(DEFAULT_QUERY)


    init {
        getRandomMeals()
        getCountryMeals()
    }

    companion object {
        const val DEFAULT_QUERY = "Egyptian"
    }

    fun updateQueryValue(query: String) {
        this.query.value = query
    }

    private fun getRandomMeals() =
        useCases.randomMealUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _randomMealsState.value = RandomMealState(
                        randomMeal = result.data
                    )
                }
                is Resource.Error -> {
                    _randomMealsState.value = RandomMealState(
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    _randomMealsState.value = RandomMealState(
                        loading = true
                    )
                }
            }
        }.launchIn(viewModelScope)


    fun getCountryMeals() =
        useCases.countryMealsUseCase(query.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _countryMealsState.value = RandomMealState(
                        randomMeal = result.data
                    )
                }
                is Resource.Error -> {
                    _countryMealsState.value = RandomMealState(
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    _countryMealsState.value = RandomMealState(
                        loading = true
                    )
                }
            }
        }.launchIn(viewModelScope)

}















