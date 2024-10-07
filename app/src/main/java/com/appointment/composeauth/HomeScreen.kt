package com.appointment.composeauth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.appointment.composeauth.navigation.NavigationRoutes
import ibtikar.tania.user.ui.viewmodels.LoginViewModel


@Composable
fun HomeScreen(
    navController: NavController
) {

    Column {
        Text(text = "Login Successfull")
        Spacer(modifier = Modifier.padding(20.dp))
        Button(
            modifier = Modifier.height(40.dp),
            onClick = {
                navController.navigate(route = NavigationRoutes.Unauthenticated.NavigationRoute.route){
                    popUpTo(NavigationRoutes.Authenticated.NavigationRoute.route) {
                        inclusive = true // Removes the authenticate route from the back stack
                    }
                }
        }) {
            Text(text = "Logout")
        }
    }

}

@Preview
@Composable
fun PreviewHomeScreen(){
    //HomeScreen()
}