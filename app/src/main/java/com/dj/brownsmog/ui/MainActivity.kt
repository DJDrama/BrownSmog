package com.dj.brownsmog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import com.dj.brownsmog.datastore.DataStoreImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class MapPointerMovingState {
    IDLE, DRAGGING
}

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStore: DataStoreImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrownSmogApp(dataStore = dataStore)
        }
    }
}