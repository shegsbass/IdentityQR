package com.shegs.identityqr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.shegs.identityqr.navigation.AppNavigation
import com.shegs.identityqr.ui.theme.IdentityQRTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IdentityQRTheme {
                Surface(elevation = 5.dp){
                    val navController = rememberNavController()
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}
