package com.inmersoft.trinidadpatrimonial.onboarding.ui.transformer

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton


/****
 * TODO
 *    TERMINAR LA PANTALLA ONBOARDING USANDO TRANSFOPRMER
 *
 *
 *
 *
 */









class OnboardingViewPagerTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        val textPhotoCount = page.findViewById<TextView>(R.id.photo_count)
        val aboutBtn = page.findViewById<ImageView>(R.id.about_app)
        val materialCardView = page.findViewById<MaterialCardView>(R.id.materialCardView)
        val image = page.findViewById<ImageView>(R.id.image_view_parallax_effect)
        val ownerAvatar = page.findViewById<MaterialCardView>(R.id.ownerAvatarContainer)
        val ownerNameText = page.findViewById<TextView>(R.id.photo_owner)
        val downloadBtn = page.findViewById<FloatingActionButton>(R.id.download)


        var translation = position * (pageWidth / 2)
        image.translationX = -translation
        materialCardView.translationX = translation
        textPhotoCount.translationX = translation
        aboutBtn.translationX = translation
        ownerAvatar.translationX = translation
        ownerNameText.translationX = translation
        downloadBtn.translationX = translation

    }
}