package com.example.mealz.presentation.home_screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mealz.databinding.FragmentHomeBinding
import com.example.mealz.domain.models.Meal
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private val countryAdapter = CountryMealAdapter()
    private lateinit var idMeal: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setUpRecyclerView()

        binding.randomMealIm.setOnClickListener {
            moveFromRandomToDetails()
        }

        collectRandomMealState()
        collectCountryMealState()
        moveToDetailsScreen()


       accessChipsCountry(chips = binding.chip1)
       accessChipsCountry(chips = binding.chip2)
       accessChipsCountry(chips = binding.chip3)
       accessChipsCountry(chips = binding.chip4)
       accessChipsCountry(chips = binding.chip5)
       accessChipsCountry(chips = binding.chip6)
       accessChipsCountry(chips = binding.chip7)
       accessChipsCountry(chips = binding.chip8)
       accessChipsCountry(chips = binding.chip9)
       accessChipsCountry(chips = binding.chip10)


        return binding.root
    }


    private fun collectRandomMealState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomMealState.collectLatest { values ->
                    Glide.with(requireContext())
                        .load(values?.randomMeal?.meals?.get(0)?.strMealThumb)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.randomMealIm)

                    binding.randomMealTv.text = values?.randomMeal?.meals?.get(0)?.strMeal

                    binding.randomMealPb.isVisible = values?.loading!!


                    idMeal = values.randomMeal?.meals?.get(0)?.idMeal.toString()

                    if (values.error != null){
                        Snackbar.make(
                            binding.root,
                            values.error,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }


                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun collectCountryMealState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countryMealState.collectLatest { value ->
                    value?.randomMeal?.meals?.let { countryAdapter.submitList(it) }

                    binding.countryPb.isVisible = value?.loading!!

                    binding.chipsGroup.setChildrenEnabled(!value.loading)


                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.query.collectLatest {
                    binding.countryMealTitleTv.text = "$it Popular Foods"
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvCountryMeals.apply {
            adapter = countryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun moveToDetailsScreen() {
        countryAdapter.setOnClickListener(object : CountryMealAdapter.OnItemClick {
            override fun onItemClick(meal: Meal) {
                idMeal = meal.idMeal
                val action = HomeFragmentDirections
                    .actionHomeFragmentToDetailsFragment(idMeal)
                findNavController().navigate(action)
            }
        })
    }

    private fun moveFromRandomToDetails() {
        val action = HomeFragmentDirections
            .actionHomeFragmentToDetailsFragment(idMeal)
        findNavController().navigate(action)

    }

    private fun accessChipsCountry(chips: Chip){

        chips.setOnClickListener {
            val text = chips.text.toString()
            viewModel.updateQueryValue(text)
            viewModel.getCountryMeals()
            collectCountryMealState()

        }
    }

    private fun ChipGroup.setChildrenEnabled(enable: Boolean){
        children.forEach {
            it.isEnabled = enable
        }
    }



//    private fun accessChipsCountry() {
//        binding.chipsGroup.setOnCheckedStateChangeListener { group, checkedIds ->
//            for (i in checkedIds.indices) {
//                val chip = group.findViewById<Chip>(i)
//                val text = chip?.text?.toString()
//                Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
//                text?.let { viewModel.updateQueryValue(it) }
//                viewModel.getCountryMeals()
//                collectCountryMealState()
//            }
//        }
//    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}









