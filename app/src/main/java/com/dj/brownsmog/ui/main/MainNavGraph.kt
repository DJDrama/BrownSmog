package com.dj.brownsmog.ui.main

import com.dj.brownsmog.R

sealed class MainScreen(val route: String, val resourceId: Int){
    object BrownSmog: MainScreen("brownsmog", R.string.brown_smog)
    object FindLocation: MainScreen("findlocation", R.string.find_location)
    object MyInformation: MainScreen("myinformation", R.string.my_information)

    object Local: MainScreen("local", R.string.local)
    object LocalCovidList:MainScreen("localcovidlist", R.string.local_covid)

    object LocalBrownSmogList: MainScreen("localdetaillist", R.string.local_detail_list)
    object LocalBrownSmogDetailInfo: MainScreen("localdetailinfo", R.string.local_detail_info)

    object CovidCounter: MainScreen("covidcounter", R.string.covid)
}

val navItems = listOf(
    MainScreen.BrownSmog,
    MainScreen.Local,
    MainScreen.MyInformation
)