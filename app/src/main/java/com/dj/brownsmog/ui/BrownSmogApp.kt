package com.dj.brownsmog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.dj.brownsmog.datastore.DataStoreImpl
import com.dj.brownsmog.ui.auth.Login
import com.dj.brownsmog.ui.main.Main
import com.dj.brownsmog.ui.theme.BrownSmogTheme

@ExperimentalComposeUiApi
@Composable
fun BrownSmogApp(dataStore: DataStoreImpl) {
    BrownSmogTheme(darkTheme = false) {
        if (dataStore.userId.value != -1) {
            Main()
        } else {
            Login()
        }
    }
}


