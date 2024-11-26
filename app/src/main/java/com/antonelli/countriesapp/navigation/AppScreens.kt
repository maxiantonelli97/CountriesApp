package com.antonelli.countriesapp.navigation

sealed class AppScreens(
    val route: String
) {

    data object HomeScreen : AppScreens( "home_screen")

    data object ErrorScreen : AppScreens( "error_screen")

    data object DetailScreen : AppScreens( "detail_screen")
}
