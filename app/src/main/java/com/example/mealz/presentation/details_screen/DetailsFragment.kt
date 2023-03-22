package com.example.mealz.presentation.details_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mealz.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()
    private var videoLink: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

        binding.youtubeIm.setOnClickListener {
            openMealVideoRecipe()
        }

        collectMealIngredientState(args.idMeal)

        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun collectMealIngredientState(idMeal: String){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getMealIngredientState(idMeal)
                viewModel.mealIngredientState.collectLatest { value ->
                    Glide.with(this@DetailsFragment)
                        .load(value?.randomMeal?.meals?.get(0)?.strMealThumb)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.imgMealDetail)

                    binding.tvCategoryInfo.text =
                        "Category: ${value?.randomMeal?.meals?.get(0)?.strCategory}"

                    binding.tvAreaInfo.text =
                        "Area: ${value?.randomMeal?.meals?.get(0)?.strArea}"

                    binding.tvContent.text =
                        value?.randomMeal?.meals?.get(0)?.strInstructions

                    binding.collapsingToolbar.title =
                        value?.randomMeal?.meals?.get(0)?.strMeal

                    binding.progressBar.visibility = View.INVISIBLE

                    binding.progressBar.isVisible = value?.loading!!

                    videoLink = value.randomMeal?.meals?.get(0)?.strYoutube
                    binding.youtubeIm.isVisible = !value.loading && value.error.isNullOrEmpty()

                    if (value.error != null){
                        Snackbar.make(
                            binding.root,
                            value.error,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }
    }

    private fun openMealVideoRecipe(){
        if (videoLink != null){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoLink)))
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}