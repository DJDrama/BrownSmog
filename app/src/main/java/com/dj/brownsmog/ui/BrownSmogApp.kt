package com.dj.brownsmog.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.dj.brownsmog.R
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.datastore.DataStoreImpl
import com.dj.brownsmog.ui.auth.Login
import com.dj.brownsmog.ui.main.Main
import com.dj.brownsmog.ui.main.Screen
import com.dj.brownsmog.ui.main.home.FindLocationScreen
import com.dj.brownsmog.ui.main.home.Home
import com.dj.brownsmog.ui.main.home.HomeViewModel
import com.dj.brownsmog.ui.main.local.Local
import com.dj.brownsmog.ui.main.local.LocalDetailInfoScreen
import com.dj.brownsmog.ui.main.local.LocalDetailListScreen
import com.dj.brownsmog.ui.main.local.LocalDetailListViewModel
import com.dj.brownsmog.ui.main.navItems
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


