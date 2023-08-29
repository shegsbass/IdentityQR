package com.shegs.identityqr.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shegs.identityqr.navigation.bottomnav.BottomNavItem
import com.shegs.identityqr.ui.screens.BottomNavigationBar
import com.shegs.identityqr.ui.screens.DashboardScreen
import com.shegs.identityqr.ui.screens.DisplayInfoScreen
import com.shegs.identityqr.ui.screens.InputInfoScreen
import com.shegs.identityqr.ui.screens.QRDisplayScreen
import com.shegs.identityqr.ui.viewmodel.InformationViewModel
import com.shegs.identityqr.util.generateQRCode

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel: InformationViewModel = hiltViewModel()

    val items = listOf(
        BottomNavItem.History,
        BottomNavItem.Create,
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = items)
        }
    ){innerPadding ->
        NavHost(navController = navController, startDestination = BottomNavItem.History.screenRoute){
            composable(BottomNavItem.History.screenRoute){
                DashboardScreen(viewModel = viewModel, navController = navController)
            }

            composable(BottomNavItem.Create.screenRoute){
                InputInfoScreen(viewModel = viewModel, navController = navController, onGenerateQRCode = { qrText ->
                    val qrText = generateQRCode(qrText)
                    navController.navigate("qrCodeDisplay/$qrText")
                } )
            }

            composable("qrCodeDisplay/{qrText}",
                arguments = listOf(navArgument("qrText"){ type = NavType.StringType})
            ){backStackEntry ->
                val qrText = backStackEntry.arguments?.getString("qrText") ?: ""
                Log.d("QRCode", "QR Text Content: $qrText")
                val bitmap = generateQRCode(qrText, 300)
                val imageBitmap: ImageBitmap? = (bitmap?.asImageBitmap())
                if (imageBitmap != null) {
                    QRDisplayScreen(imageBitmap, navController)
                }
            }

            composable(
                route = "displayInfo/{infoId}",
                arguments = listOf(navArgument("infoId") { type = NavType.IntType })
            ) { backStackEntry ->
                // Retrieve the infoId from the route
                val infoId = backStackEntry.arguments?.getInt("infoId") ?: 0
                DisplayInfoScreen(infoId, viewModel, navController)
            }
        }
    }


}
