package com.dj.brownsmog.ui.main.local.covid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.brownsmog.data.model.SidoByulCounter
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.repository.main.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalCovidViewModel
@Inject
constructor(private val repository: LocalRepository) : ViewModel() {

    private val _sidoByulItems: MutableStateFlow<List<SidoByulCounter>?> = MutableStateFlow(null)
    val sidoByulItems: StateFlow<List<SidoByulCounter>?>
        get() = _sidoByulItems

    private val _onLoad = MutableStateFlow(false)
    val onLoad: StateFlow<Boolean>
        get() = _onLoad

    fun getLocalCounter() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCovidInformation()
            _sidoByulItems.value = response
        }
    }

    fun setLoaded() {
        _onLoad.value = true
    }
}