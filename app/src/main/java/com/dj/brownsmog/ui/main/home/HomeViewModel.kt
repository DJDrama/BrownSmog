package com.dj.brownsmog.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.brownsmog.data.model.Data
import com.dj.brownsmog.data.model.SidoByulCounter
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.repository.main.HomeRepository
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
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

    private val _data = MutableStateFlow<Data?>(null)
    val data: StateFlow<Data?>
        get() = _data

    private val _covidInformation = MutableStateFlow<SidoByulCounter?>(null)
    val covidInformation: StateFlow<SidoByulCounter?>
        get() = _covidInformation



    private var brownSmogJob: Job? = null
    private var covidJob: Job? = null

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
                getMyLocation()
                _locationUpdate.value = it
            }
        }
    }

    fun setUpdateComplete() {
        _locationUpdate.value = false
    }

    fun getBrownSmogFromMyLocation(latitude: Double, longitude: Double) {
        if (brownSmogJob?.isActive == true)
            return
        brownSmogJob = viewModelScope.launch {
            val res = homeRepository.getBrownSmogFromMyLocation(latitude, longitude)
            _data.value = res
        }
    }

    fun getCovidCounter(latitude: Double, longitude: Double) {
        if (covidJob?.isActive == true)
            return
        covidJob = viewModelScope.launch {
            val res = homeRepository.getCovidInformation(latitude, longitude)
            _covidInformation.value = res
        }
    }
}