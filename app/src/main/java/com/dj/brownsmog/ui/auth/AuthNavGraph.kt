package com.dj.brownsmog.ui.auth

import com.dj.brownsmog.R

sealed class AuthScreen(val route: String, val resourceId: Int){
    object LoginScreen: AuthScreen("login", R.string.login)
    object RegisterScreen: AuthScreen("register", R.string.register)
}

