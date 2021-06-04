package com.inmersoft.trinidadpatrimonial.map.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inmersoft.trinidadpatrimonial.core.data.DataRepository
import com.inmersoft.trinidadpatrimonial.core.data.entity.Place
import com.inmersoft.trinidadpatrimonial.core.data.entity.PlaceType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapFragmentViewModel @Inject constructor(repository: DataRepository) :
    ViewModel() {
    val allPlaceType: LiveData<List<PlaceType>> = repository.allPlacesType
    val allPlaces: LiveData<List<Place>> = repository.allPlaces
}
