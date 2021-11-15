package com.piconemarc.personalaccountmanager.ui.screen

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument


interface NavDestination {

    val destination: String
    val key: String get() = ""
    val arguments: List<NamedNavArgument> get() = emptyList()

    fun doNavigation(navController: NavController) {
            navController.navigate(destination) {
                popUpTo(NavDestinations.Home.destination) {}
        }
    }

    fun doNavigation(navController: NavController,  argument : String = "") {
            navController.navigate("$destination/${argument}") {
                popUpTo(NavDestinations.Home.destination) {}
                launchSingleTop = true
        }
    }

    fun getRoute(): String = if (key.trim().isEmpty()) destination
    else "${destination}/{$key}"

}


object NavDestinations {

    val Home = object : NavDestination {
        override val destination: String = "myAccount"
    }

    val myAccountDetail = object : NavDestination {
        override val destination: String = "myAccountDetail"
        override val key: String = "selectedAccountId"
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(key) { type = NavType.StringType }
        )
    }

    val myPayment = object : NavDestination {
        override val destination: String = "myPayment"
    }

}