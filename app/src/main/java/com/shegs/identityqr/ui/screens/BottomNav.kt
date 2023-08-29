package com.shegs.identityqr.ui.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shegs.identityqr.navigation.bottomnav.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavHostController,items: List<BottomNavItem>) {


            BottomNavigation {
                val currentRoute = currentRoute(navController)


                items.forEach { bottomNavItem ->
                    BottomNavigationItem(
                        icon = {
                            val painter = painterResource(bottomNavItem.icon)
                            Icon(painter = painter, contentDescription = null)
                        },
                        unselectedContentColor = Color.Gray,
                        label = { Text(bottomNavItem.title) },
                        selected = currentRoute == bottomNavItem.screenRoute,
                        onClick = {
                            if (currentRoute != bottomNavItem.screenRoute) {
                                navController.navigate(bottomNavItem.screenRoute)
                            }
                        }
                    )
                }

        }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    return currentDestination?.route
}