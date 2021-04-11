package com.dj.brownsmog.ui

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
import com.dj.brownsmog.ui.home.Home
import com.dj.brownsmog.ui.local.Local
import com.dj.brownsmog.ui.local.LocalDetailScreen
import com.dj.brownsmog.ui.local.LocalDetailViewModel
import com.dj.brownsmog.ui.theme.BrownSmogTheme

@Composable
fun BrownSmogApp() {
    /** DarkTheme false now, TODO: will be fixed **/
    BrownSmogTheme(darkTheme = false) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
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

        ) {
            NavHost(navController, startDestination = Screen.BrownSmog.route) {
                composable(Screen.BrownSmog.route) {
                    Home(navController = navController)
                }
                composable(Screen.LocalSmog.route) {
                    Local(onNavigate = {
                        navController.navigate(route = it)
                    })
                }
                composable(route = Screen.LocalDetail.route + "/{sidoName}",
                    arguments = listOf(
                        navArgument("sidoName") {
                            type = NavType.StringType
                        }
                    )) { navBackStackEntry ->
                    val viewModel = viewModel<LocalDetailViewModel>(
                        Screen.LocalDetail.route,
                        HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    )
                    LocalDetailScreen(navController = navController,
                        viewModel = viewModel,
                        sidoName = navBackStackEntry.arguments?.getString("sidoName") ?: "")
                }
            }
        }
    }
}


