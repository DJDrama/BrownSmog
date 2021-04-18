package com.dj.brownsmog.ui.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalDetailListViewModel
@Inject
constructor(
    private val repository: LocalRepository,
) : ViewModel() {

    private val _sidoByulItems: MutableStateFlow<List<SidoItem>?> = MutableStateFlow(null)
    val sidoByulItems: StateFlow<List<SidoItem>?>
        get() = _sidoByulItems

    private val _onLoad = MutableStateFlow(false)
    val onLoad: StateFlow<Boolean>
        get() = _onLoad

    fun getSidoByulItems(sidoName: String) {
        viewModelScope.launch(IO) {
            val measuredDatum = repository.getSidoMeasuredData(sidoName = sidoName)
            measuredDatum?.let {
                _sidoByulItems.value = it
            }
        }
    }
    fun setLoaded(){
        _onLoad.value = true
    }
}