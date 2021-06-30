package com.inmersoft.trinidadpatrimonial.onboarding.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.inmersoft.trinidadpatrimonial.R
import com.inmersoft.trinidadpatrimonial.databinding.FragmentOnboardingBinding
import com.inmersoft.trinidadpatrimonial.onboarding.data.OnBoardingData
import com.inmersoft.trinidadpatrimonial.onboarding.ui.adapters.OnBoardingAdapter
import com.inmersoft.trinidadpatrimonial.onboarding.ui.transformer.OnboardingViewPagerTransformer
import com.inmersoft.trinidadpatrimonial.utils.fadeTransition
import com.inmersoft.trinidadpatrimonial.viewmodels.TrinidadDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    lateinit var binding: FragmentOnboardingBinding

    val trinidadDataViewModel: TrinidadDataViewModel by activityViewModels()

    private val viewPager2PageChangeCallback = ViewPager2PageChangeCallback {
        setOnboardingPoint(it)
    }

    private val onboardingAdapter by lazy {
        OnBoardingAdapter(
            listOf(
                OnBoardingData(
                    resources.getString(R.string.onboarding_title_page1),
                    resources.getString(R.string.onboarding_subtitle_page1),
                    R.drawable.ic_onboarding_page_1
                ), OnBoardingData(
                    resources.getString(R.string.onboarding_title_page2),
                    resources.getString(R.string.onboarding_subtitle_page2),
                    R.drawable.ic_onboarding_page_2
                ), OnBoardingData(
                    resources.getString(R.string.onboarding_title_page3),
                    resources.getString(R.string.onboarding_subtitle_page3),
                    R.drawable.ic_onboarding_page_3
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentOnboardingBinding.inflate(inflater, container, false)
        binding.onboardingStartButton.setOnClickListener {

            val extras =
                FragmentNavigatorExtras(
                    binding.onboardingStartButton to "home_fragment_container"
                )
            val action = OnboardingFragmentDirections.actionOnboardingFragmentToNavHome()
            findNavController().navigate(action, extras)
        }

        binding.onboardingViewPage.adapter = onboardingAdapter

        binding.onboardingViewPage.setPageTransformer(OnboardingViewPagerTransformer())

        populateOnboardingPoints()

        setOnboardingPoint(0)

        binding.onboardingViewPage.registerOnPageChangeCallback(viewPager2PageChangeCallback)

        trinidadDataViewModel.allPlacesName.observe(viewLifecycleOwner, {
            Log.d("DATABASE_POPULATE", "initDataBase: DATABASE: ${it.size}")
        })

        return binding.root
    }

    private fun populateOnboardingPoints() {
        for (i in 0 until onboardingAdapter.itemCount) {
            val imv = ImageView(requireContext())
            imv.setImageResource(R.drawable.onboarding_item_unselected)
            binding.onboardingPagePositionContainer.addView(imv)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.onboardingViewPage.unregisterOnPageChangeCallback(viewPager2PageChangeCallback)
    }

    private fun setOnboardingPoint(index: Int) {
        val max = binding.onboardingPagePositionContainer.size
        (0 until max).forEach { currentPoint ->
            val imv = binding.onboardingPagePositionContainer.getChildAt(currentPoint) as ImageView
            if (currentPoint == index)
                imv.setImageResource(R.drawable.onboarding_item_selected)
            else
                imv.setImageResource(R.drawable.onboarding_item_unselected)
        }

        if (index == max - 1) {
            fadeTransition(binding.container)
            binding.onboardingPagePositionContainer.visibility = View.INVISIBLE
            binding.onboardingStartButton.visibility = View.VISIBLE
        } else {
            fadeTransition(binding.container)
            if (binding.onboardingPagePositionContainer.visibility == View.INVISIBLE) {
                binding.onboardingPagePositionContainer.visibility = View.VISIBLE
            }
            binding.onboardingStartButton.visibility = View.INVISIBLE
        }
    }
}

class ViewPager2PageChangeCallback(private val listener: (Int) -> Unit) :
    ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        listener.invoke(position)
    }
}