package com.dj.brownsmog.ui.main.me

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class UserViewModel
@Inject
constructor(
    private val repository: UserRepository,
    private val dataStoreImpl: DataStoreImpl
) : ViewModel() {
    private val _user: MutableStateFlow<UserEntity?> = MutableStateFlow(null)
    val user: StateFlow<UserEntity?>
        get() = _user

    init{
        getMyInformation()
    }
    private fun getMyInformation(){
        viewModelScope.launch {
            repository.getMyInformation().collect{
                _user.value = it
            }
        }
    }

    fun logOut(){
        dataStoreImpl.logOut()
    }
    fun exit(){
        viewModelScope.launch {
            repository.exitMember().collect {
                if(it){
                    dataStoreImpl.logOut()
                }
            }
        }
    }
}