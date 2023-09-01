package com.shegs.identityqr.navigation.bottomnav

import com.shegs.identityqr.R

sealed class NavItem(var title: String, var icon: Int, var screenRoute: String){
    object History : NavItem("History", R.drawable.history, "dashboard" )
    object Create : NavItem("Create Card", R.drawable.add_circle, "addInfo" )
}
