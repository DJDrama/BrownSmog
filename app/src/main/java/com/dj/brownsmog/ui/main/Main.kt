package com.dj.brownsmog.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Person
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
import com.dj.brownsmog.ui.main.home.FindLocationScreen
import com.dj.brownsmog.ui.main.home.Home
import com.dj.brownsmog.ui.main.home.HomeViewModel
import com.dj.brownsmog.ui.main.local.Local
import com.dj.brownsmog.ui.main.local.LocalDetailInfoScreen
import com.dj.brownsmog.ui.main.local.LocalDetailListScreen
import com.dj.brownsmog.ui.main.local.LocalDetailListViewModel
import com.dj.brownsmog.ui.main.me.MyInformation
import com.dj.brownsmog.ui.main.me.UserViewModel

@ExperimentalComposeUiApi
@Composable
fun Main(){
    val navController = rememberNavController()
    val visible = remember { mutableStateOf(true) }
    val context = LocalContext.current
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

                    BottomNavigationItem(
                        icon = {
                            Icon(imageVector = Icons.Filled.Person,
                                contentDescription = stringResource(
                                    id = R.string.my_information))
                        },
                        label = { Text(text = stringResource(id = R.string.my_information)) },
                        selected = currentRoute == navItems[2].route,
                        onClick = {
                            navController.navigate(navItems[2].route) {
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
            NavHost(navController, startDestination = MainScreen.BrownSmog.route) {
                composable(MainScreen.BrownSmog.route) { navBackStackEntry ->
                    visible.value = true
                    val viewModel =
                        hiltNavGraphViewModel<HomeViewModel>(backStackEntry = navBackStackEntry)
                    Home(viewModel = viewModel, onNavigate = { route ->
                        navController.navigate(route = route)
                    })
                }
                composable(MainScreen.FindLocation.route) {
                    visible.value = false
                    val viewModel =
                        navController.hiltNavGraphViewModel<HomeViewModel>(route = MainScreen.BrownSmog.route)
                    FindLocationScreen(viewModel = viewModel){
                        navController.navigateUp()
                        Toast.makeText(context, "위치가 업데이트 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }


                composable(MainScreen.LocalSmog.route) {
                    visible.value = true
                    Local(onNavigate = { route ->
                        navController.navigate(route = route)
                    })
                }
                composable(route = MainScreen.LocalDetailList.route + "/{sidoName}",
                    arguments = listOf(
                        navArgument("sidoName") {
                            type = NavType.StringType
                        }
                    )) { navBackStackEntry ->
                    visible.value = false

                    val viewModel = viewModel<LocalDetailListViewModel>(
                        MainScreen.LocalDetailList.route,
                        HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    )
                    LocalDetailListScreen(
                        viewModel = viewModel,
                        sidoName = navBackStackEntry.arguments?.getString("sidoName") ?: "",
                        onClick = { sidoItem ->
                            navController.currentBackStackEntry?.arguments?.putParcelable("sidoItem",
                                sidoItem)
                            navController.navigate(route =
                            MainScreen.LocalDetailInfo.route + "/${sidoItem.stationName}"
                            )
                        }
                    )
                }
                composable(route = MainScreen.LocalDetailInfo.route + "/{stationName}",
                    arguments = listOf(
                        navArgument("sidoItem") {
                            type = NavType.ParcelableType(SidoItem::class.java)
                        }
                    )) {
                    visible.value = false

                    navController.previousBackStackEntry?.arguments?.getParcelable<SidoItem>(
                        "sidoItem")
                        ?.let { sidoItem ->
                            LocalDetailInfoScreen(sidoItem = sidoItem, upPress = {
                                navController.navigateUp()
                            })
                        }
                }

                composable(MainScreen.MyInformation.route) {
                    visible.value = true
                    val viewModel =
                        navController.hiltNavGraphViewModel<UserViewModel>(route = MainScreen.MyInformation.route)
                    MyInformation(viewModel)
                }
            }
        }

    }
}
