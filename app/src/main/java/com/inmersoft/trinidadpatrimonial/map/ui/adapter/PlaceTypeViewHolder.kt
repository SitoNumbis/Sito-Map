package com.inmersoft.trinidadpatrimonial.map.ui.adapter

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inmersoft.trinidadpatrimonial.R
import com.inmersoft.trinidadpatrimonial.core.data.entity.PlaceTypeWithPlaces
import com.inmersoft.trinidadpatrimonial.databinding.MapItemPlaceTypeBinding
import com.inmersoft.trinidadpatrimonial.utils.TrinidadAssets

class PlaceTypeViewHolder(
    private val binding: MapItemPlaceTypeBinding

) :
    RecyclerView.ViewHolder(binding.root) {
    private val placeTypeImage: ImageView = binding.placeTypeImage

    fun bindData(itemPlaceType: PlaceTypeWithPlaces) {
        binding.placeType = itemPlaceType.placeType
        val file=Uri.parse(TrinidadAssets.getAssetFullPath(itemPlaceType.placeType.icon,TrinidadAssets.FILE_JPG_EXTENSION))
        Log.d("TAG", "bindData: $file")
        Glide.with(binding.root.context)
            .load(
                file.toString()
            )
            .error(R.drawable.placeholder_error)
            .placeholder(R.drawable.placeholder_error)
            .into(placeTypeImage)
    }

}