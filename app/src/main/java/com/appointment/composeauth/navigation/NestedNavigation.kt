package com.appointment.composeauth.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.appointment.composeauth.HomeScreen
import com.appointment.composeauth.LoginScreen
import com.appointment.composeauth.SignupScreen
import ibtikar.tania.user.ui.viewmodels.LoginViewModel


/**
 * Login, registration, forgot password screens nav graph builder
 * (Unauthenticated user)
 */
fun NavGraphBuilder.unauthenticatedGraph(navController: NavController) {

    navigation(
        route = NavigationRoutes.Unauthenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Unauthenticated.Login.route
    ) {

        // Login
        composable(route = NavigationRoutes.Unauthenticated.Login.route) {
            LoginScreen(
                navigateToDashboard = {
                    navController.navigate(route = NavigationRoutes.Authenticated.NavigationRoute.route){
                        popUpTo(NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                            inclusive = true // Removes the unauthenticate route from the back stack
                        }
                    } },
                navigateToSignup = {
                    navController.navigate(route = NavigationRoutes.Unauthenticated.Registration.route)},
                )
        }

        // Registration
        composable(route = NavigationRoutes.Unauthenticated.Registration.route) {
            SignupScreen(
                navigateToLogin = {
                    navController.navigate(route = NavigationRoutes.Unauthenticated.Login.route)
                }
            )
            /*RegistrationScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToAuthenticatedRoute = {
                    navController.navigate(route = NavigationRoutes.Authenticated.NavigationRoute.route) {
                        popUpTo(route = NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                            inclusive = true
                        }
                    }
                }
            )*/
        }
    }
}

/**
 * Authenticated screens nav graph builder
 */
fun NavGraphBuilder.authenticatedGraph(navController: NavController) {
    navigation(
        route = NavigationRoutes.Authenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Authenticated.Dashboard.route
    ) {
        // Dashboard
        composable(route = NavigationRoutes.Authenticated.Dashboard.route) {
            HomeScreen(navController)
        }
    }
}