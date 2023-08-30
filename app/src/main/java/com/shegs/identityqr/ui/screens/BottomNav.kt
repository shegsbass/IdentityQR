package com.shegs.identityqr.ui.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shegs.identityqr.navigation.bottomnav.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavHostController,items: List<BottomNavItem>) {


            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.background
            ) {
                val currentRoute = currentRoute(navController)


                items.forEach { bottomNavItem ->
                    BottomNavigationItem(
                        icon = {
                            val painter = painterResource(bottomNavItem.icon)
                            Icon(
                                painter = painter,
                                contentDescription = null,
                                tint = if (currentRoute == bottomNavItem.screenRoute) {
                                    MaterialTheme.colorScheme.inversePrimary // Selected color
                                } else {
                                    MaterialTheme.colorScheme.onPrimary // Unselected color
                                }
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.inversePrimary,
                        label = {
                            Text(
                                text = bottomNavItem.title,
                                color = if (currentRoute == bottomNavItem.screenRoute) {
                                    MaterialTheme.colorScheme.inversePrimary // Selected color
                                } else {
                                    MaterialTheme.colorScheme.onPrimary // Unselected color
                                },
                                fontSize = if (currentRoute == bottomNavItem.screenRoute) {
                                    12.sp // Selected text size
                                } else {
                                    10.sp // Unselected text size
                                }

                            )
                                },
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