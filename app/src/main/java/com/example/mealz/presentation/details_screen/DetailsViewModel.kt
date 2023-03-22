package com.example.mealz.presentation.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealz.domain.usecase.AllUseCases
import com.example.mealz.presentation.home_screen.RandomMealState
import com.example.mealz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: AllUseCases
) : ViewModel() {


    private val _mealIngredientState: MutableStateFlow<RandomMealState?> =
        MutableStateFlow(null)

    val mealIngredientState = _mealIngredientState.asStateFlow()


    fun getMealIngredientState(idMeal: String) =
        useCases.mealIngredients(idMeal).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _mealIngredientState.value = RandomMealState(
                        randomMeal = result.data
                    )
                }
                is Resource.Error -> {
                    _mealIngredientState.value = RandomMealState(
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    _mealIngredientState.value = RandomMealState(
                        loading = true
                    )
                }
            }
        }.launchIn(viewModelScope)


}