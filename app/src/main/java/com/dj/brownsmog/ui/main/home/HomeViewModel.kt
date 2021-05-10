package com.dj.brownsmog.ui.main.home

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.repository.main.HomeRepository
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _myLocation: MutableStateFlow<LocationEntity?> = MutableStateFlow(null)
    val myLocation: StateFlow<LocationEntity?>
        get() = _myLocation

    private val _locationUpdate = MutableStateFlow(false)
    val locationUpdate: StateFlow<Boolean>
        get() = _locationUpdate

    init {
        getMyLocation()
    }

    private fun getMyLocation() {
        viewModelScope.launch {
            homeRepository.getMyLocation().collect {
                it?.let {
                    _myLocation.value = it

                }
            }
        }
    }

    fun saveMyLocation(latLng: LatLng) {
        viewModelScope.launch {
            homeRepository.saveMyLocation(latLng).collect {
                _locationUpdate.value = it
            }
        }
    }

    fun setUpdateComplete() {
        _locationUpdate.value = false
    }

    fun getBrownSmogFromMyLocation(list: List<Address>) {
        viewModelScope.launch {
            val curr = list[0]
            val locality = curr.locality
            val res = homeRepository.getBrownSmogFromMyLocation(locality)
            Log.e("Debug", "Debug : " + res)
        }
    }
}