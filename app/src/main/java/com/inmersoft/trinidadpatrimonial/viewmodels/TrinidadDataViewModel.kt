package com.inmersoft.trinidadpatrimonial.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inmersoft.trinidadpatrimonial.core.data.DataRepository
import com.inmersoft.trinidadpatrimonial.core.data.entity.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TrinidadDataViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    var allPlacesName = dataRepository.allPlacesName
    var allPlaceTypeWithPlaces = dataRepository.allPlacesTypeWithPlaces
    var allPlaces = dataRepository.allPlaces

    private val _currentPlaceToBottomSheet = MutableLiveData<Place?>()
    val currentPlaceToBottomSheet: LiveData<Place?> get() = _currentPlaceToBottomSheet

    fun onBottomSheetShow(placeId: Int) {
        viewModelScope.launch {
            val place = withContext(Dispatchers.IO) { dataRepository.getPlaceById(placeId) }
            _currentPlaceToBottomSheet.value = place
            Log.d("TAG", "onBottomSheetShow: Executing coroutine")
        }
    }

    fun onMapDestroy() {
        _currentPlaceToBottomSheet.value = null
    }

}