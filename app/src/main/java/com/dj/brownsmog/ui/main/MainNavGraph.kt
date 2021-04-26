package com.dj.brownsmog.ui.main

import com.dj.brownsmog.R

sealed class MainScreen(val route: String, val resourceId: Int){
    object BrownSmog: MainScreen("brownsmog", R.string.brown_smog)
    object FindLocation: MainScreen("findlocation", R.string.find_location)
    object MyInformation: MainScreen("myinformation", R.string.my_information)

    object LocalSmog: MainScreen("localsmog", R.string.local_smog)
    object LocalDetailList: MainScreen("localdetaillist", R.string.local_detail_list)
    object LocalDetailInfo: MainScreen("localdetailinfo", R.string.local_detail_info)
}

val navItems = listOf(
    MainScreen.BrownSmog,
    MainScreen.LocalSmog,
    MainScreen.MyInformation
)