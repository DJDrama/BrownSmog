package com.dj.brownsmog.ui.local

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel
@Inject
constructor(
    val repository: HomeRepository,
) : ViewModel() {

    private val _sidoByulItems: MutableStateFlow<List<SidoItem>?> = MutableStateFlow(null)
    val sidoByulItems: StateFlow<List<SidoItem>?>
        get() = _sidoByulItems

    init {
        getSidoByulItems()
    }

    fun getSidoByulItems() {
        viewModelScope.launch(IO) {
            _sidoByulItems.value = repository.getSidoMeasuredData() ?: emptyList()
            Log.e("check", "come ehere? " + _sidoByulItems.value?.size)

        }
    }
}