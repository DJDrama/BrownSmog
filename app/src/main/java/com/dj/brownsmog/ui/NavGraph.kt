package com.dj.brownsmog.ui

import androidx.compose.ui.res.stringResource
import com.dj.brownsmog.R

sealed class Screen(val route: String, val resourceId: Int){
    object BrownSmog: Screen("brownsmog", R.string.brown_smog)
    object FindLocation: Screen("findlocation", R.string.find_location)

    object LocalSmog: Screen("localsmog", R.string.local_smog)
    object LocalDetailList: Screen("localdetaillist", R.string.local_detail_list)
    object LocalDetailInfo: Screen("localdetailinfo", R.string.local_detail_info)
}

val navItems = listOf(
    Screen.BrownSmog,
    Screen.LocalSmog
)