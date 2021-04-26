package com.dj.brownsmog.datastore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.dj.brownsmog.BaseApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreImpl
@Inject
constructor(
    private val app: BaseApplication,
) {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _userId = mutableStateOf(-1)
    val userId: State<Int>
        get() = _userId

    init {
        observeUser()
    }

    fun setUserId(id: Int) {
        scope.launch {
            app.dataStore.edit { pref ->
                pref[USER_ID] = id
            }
        }
    }

    fun logOut(){
        setUserId(-1)
    }

    private fun observeUser() {
        app.dataStore.data.onEach { pref ->
            pref[USER_ID]?.let {
                _userId.value = it
            }
        }.launchIn(scope = scope)
    }

    companion object {
        private val USER_ID = intPreferencesKey("user_id")
    }
}