package app.ibiocd.appointment.home.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import app.ibiocd.appointment.home.viewmodel.HomeViewModel
import app.ibiocd.appointment.home.navegation.HomeDestinations.*
import app.ibiocd.appointment.presentation.screens.Home.Notification
import com.nopalsoft.simple.rest.presentation.*


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationMedicalHost(
    navControllerHome: NavHostController,
    navController: NavHostController,
    Medical: Boolean,
    img: String,
    name: String,
    ID: String,
) {

    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) {
            Home(Medical, img, name, ID, navControllerHome)
        }
        composable(Calendar.route) {
            Calendar(Medical)
        }
        composable(Files.route, arguments = listOf(navArgument("newText") { defaultValue = "0" }))
        { navBackStackEntry ->
            var newText = navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)


            FilesMedical(newText.toInt(), Medical, ID)


        }
        composable(Menu.route) {
            MenuMedical(
                navegarFile = { newText ->
                    navController.navigate(Files.route)
                },
                navegarCalendar = {
                    navController.navigate(Calendar.route)
                }, ID, name, img
            )
        }
        composable(
            Notification.route,
            arguments = listOf(navArgument("newText") { defaultValue = "Pantalla 2" })
        )
        { navBackStackEntry ->
            val newText = navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)

            Notification()
        }
    }
}