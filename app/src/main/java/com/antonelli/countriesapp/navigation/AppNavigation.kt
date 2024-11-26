package com.antonelli.countriesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.antonelli.countriesapp.entity.CountryModel
import com.antonelli.countriesapp.ui.detail.detailScreen
import com.antonelli.countriesapp.ui.error.errorScreen
import com.antonelli.countriesapp.ui.main.mainScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Composable
fun appNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(AppScreens.HomeScreen.route) {
            mainScreen(navController)
        }
        composable(
            AppScreens.ErrorScreen.route + "/{start}",
            arguments = listOf(navArgument("start") { type = NavType.StringType }),
        ) { backStackEntry ->
            errorScreen(navController, backStackEntry.arguments?.getString("start") ?: AppScreens.HomeScreen.route)
        }
        composable(
            AppScreens.DetailScreen.route,
        ) {
            val c = it.arguments?.getParcelable<CountryModel>("country")
            detailScreen(navController, c)
        }
    }
}
