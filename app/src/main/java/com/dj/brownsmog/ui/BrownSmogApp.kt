package com.dj.brownsmog.ui

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.HiltViewModelFactory
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
import com.dj.brownsmog.ui.home.Home
import com.dj.brownsmog.ui.home.HomeViewModel
import com.dj.brownsmog.ui.local.Local
import com.dj.brownsmog.ui.local.LocalDetailInfoScreen
import com.dj.brownsmog.ui.local.LocalDetailListScreen
import com.dj.brownsmog.ui.local.LocalDetailListViewModel
import com.dj.brownsmog.ui.theme.BrownSmogTheme

@Composable
fun BrownSmogApp() {
    /** DarkTheme false now, TODO: will be fixed **/
    BrownSmogTheme(darkTheme = false) {
        val navController = rememberNavController()
        val visible = remember { mutableStateOf(true) }
        Scaffold(
            bottomBar = {
                if (visible.value) {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                        BottomNavigationItem(
                            icon = {
                                Icon(imageVector = Icons.Filled.BlurOn,
                                    contentDescription = stringResource(
                                        id = R.string.brown_smog))
                            },
                            label = { Text(text = stringResource(id = R.string.brown_smog)) },
                            selected = currentRoute == navItems[0].route,
                            onClick = {
                                navController.navigate(navItems[0].route) {
                                    popUpTo = navController.graph.startDestination
                                    launchSingleTop = true
                                }
                            }
                        )
                        BottomNavigationItem(
                            icon = {
                                Icon(imageVector = Icons.Filled.Place,
                                    contentDescription = stringResource(
                                        id = R.string.local_smog))
                            },
                            label = { Text(text = stringResource(id = R.string.local_smog)) },
                            selected = currentRoute == navItems[1].route,
                            onClick = {
                                navController.navigate(navItems[1].route) {
                                    popUpTo = navController.graph.startDestination
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }

        ) {
            Box(modifier = Modifier.padding(it)) {
                NavHost(navController, startDestination = Screen.BrownSmog.route) {
                    composable(Screen.BrownSmog.route) { navBackStackEntry ->
                        visible.value = true
                        val viewModel = viewModel<HomeViewModel>(
                            Screen.BrownSmog.route,
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        )
                        Home(viewModel = viewModel)
                    }
                    composable(Screen.LocalSmog.route) {
                        visible.value = true
                        Local(onNavigate = { route ->
                            navController.navigate(route = route)
                        })
                    }
                    composable(route = Screen.LocalDetailList.route + "/{sidoName}",
                        arguments = listOf(
                            navArgument("sidoName") {
                                type = NavType.StringType
                            }
                        )) { navBackStackEntry ->
                        visible.value = false

                        val viewModel = viewModel<LocalDetailListViewModel>(
                            Screen.LocalDetailList.route,
                            HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                        )
                        LocalDetailListScreen(
                            viewModel = viewModel,
                            sidoName = navBackStackEntry.arguments?.getString("sidoName") ?: "",
                            onClick = { sidoItem ->
                                navController.currentBackStackEntry?.arguments?.putParcelable("sidoItem",
                                    sidoItem)
                                navController.navigate(route =
                                Screen.LocalDetailInfo.route + "/${sidoItem.stationName}"
                                )
                            }
                        )
                    }
                    composable(route = Screen.LocalDetailInfo.route + "/{stationName}",
                        arguments = listOf(
                            navArgument("sidoItem") {
                                type = NavType.ParcelableType(SidoItem::class.java)
                            }
                        )) {
                        visible.value = false

                        navController.previousBackStackEntry?.arguments?.getParcelable<SidoItem>("sidoItem")
                            ?.let { sidoItem ->
                                LocalDetailInfoScreen(sidoItem = sidoItem, upPress = {
                                    navController.navigateUp()
                                })
                            }
                    }
                }
            }

        }
    }
}


