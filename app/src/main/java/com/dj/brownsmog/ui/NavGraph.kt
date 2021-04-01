package com.dj.brownsmog.ui

import com.dj.brownsmog.R

sealed class Screen(val route: String, val resourceId: Int){
    object BrownSmog: Screen("brownsmog", R.string.brown_smog)
    object LocalSmog: Screen("localsmog", R.string.local_smog)
}

val navItems = listOf(
    Screen.BrownSmog,
    Screen.LocalSmog
)