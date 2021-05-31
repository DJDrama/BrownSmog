package com.dj.brownsmog.ui.main.me

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.brownsmog.data.model.LocalCounter
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.datastore.DataStoreImpl
import com.dj.brownsmog.db.UserEntity
import com.dj.brownsmog.repository.main.LocalRepository
import com.dj.brownsmog.repository.main.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CovidViewModel
@Inject
constructor(
    private val repository: UserRepository,
) : ViewModel() {
    private val _localCounter: MutableStateFlow<LocalCounter?> = MutableStateFlow(null)
    val localCounter: StateFlow<LocalCounter?>
        get() = _localCounter

    init {
        getLocalCounter()
    }

    private fun getLocalCounter() {
        viewModelScope.launch {
            repository.getLocalCounter().collect {
                it?.let{
                    _localCounter.value = it
                }
            }
        }
    }
}