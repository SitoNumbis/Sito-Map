package com.inmersoft.trinidadpatrimonial.onboarding.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inmersoft.trinidadpatrimonial.R
import com.inmersoft.trinidadpatrimonial.databinding.FragmentOnboardingScreenBinding
import com.inmersoft.trinidadpatrimonial.onboarding.data.OnBoardingData

class OnBoardingViewHolder(private val binding: FragmentOnboardingScreenBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bindData(currenOnboardingScreen: OnBoardingData) {
        Glide.with(binding.root.context)
            .load(currenOnboardingScreen.imageResource)
            .error(R.drawable.placeholder_error)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.imageViewParallaxEffect)

        binding.title.text = currenOnboardingScreen.title
        binding.subtitle.text = currenOnboardingScreen.subtitle
    }

}