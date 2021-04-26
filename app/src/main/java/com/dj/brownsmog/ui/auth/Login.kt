package com.dj.brownsmog.ui.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dj.brownsmog.ui.auth.screens.LoginScreen
import com.dj.brownsmog.ui.auth.screens.RegisterScreen
import com.dj.brownsmog.ui.main.Screen

@Composable
fun Login() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AuthScreen.LoginScreen.route) {
        composable(AuthScreen.LoginScreen.route) { navBackStackEntry ->
            val viewModel =
                hiltNavGraphViewModel<AuthViewModel>(backStackEntry = navBackStackEntry)
            LoginScreen(viewModel = viewModel)
        }
        composable(Screen.FindLocation.route) {
            val viewModel =
                navController.hiltNavGraphViewModel<AuthViewModel>(route = AuthScreen.LoginScreen.route)
            RegisterScreen(viewModel = viewModel)
        }
    }
}

