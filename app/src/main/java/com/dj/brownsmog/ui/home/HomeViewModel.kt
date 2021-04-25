package com.dj.brownsmog.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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



    init {
        getMyViewModel()
    }

    private fun getMyViewModel() {
        viewModelScope.launch {
            homeRepository.getMyLocation().collect {
                it?.let {
                    _myLocation.value = it
                }
            }
        }
    }
}