package com.shegs.identityqr.navigation.bottomnav

import com.shegs.identityqr.R

sealed class BottomNavItem(var title: String, var icon: Int, var screenRoute: String){
    object History : BottomNavItem("History", R.drawable.history, "dashboard" )
    object Create : BottomNavItem("Create Card", R.drawable.add_circle, "addInfo" )
}
