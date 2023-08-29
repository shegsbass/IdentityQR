package com.shegs.identityqr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                val navController = rememberNavController()
                AppNavigation(navController = navController)

            }
        }
    }
}
