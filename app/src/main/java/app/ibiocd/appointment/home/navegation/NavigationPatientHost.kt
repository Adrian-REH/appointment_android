package app.ibiocd.appointment.home.navegation
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import app.ibiocd.appointment.home.viewmodel.HomeViewModel
import app.ibiocd.appointment.UserViewModel
import com.nopalsoft.simple.rest.presentation.*


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationPatientHost(
    navControllerHome: NavHostController,
    navController: NavHostController,
    Medical: Boolean,
    img:String,
    name: String,
    ID: String,
) {
    NavHost(navController = navController, startDestination = HomeDestinations.Home.route) {

        composable(HomeDestinations.Home.route) {


            Home(Medical,img,name,ID, navControllerHome)
        }


        composable(HomeDestinations.Favs.route,) {
            Favs(ID)
        }

        composable(HomeDestinations.Calendar.route) {
            Calendar(Medical)
        }
        composable(
            HomeDestinations.Files.route, arguments = listOf(navArgument("newText"){ defaultValue = "0" })
        ) { navBackStackEntry ->
            val newText = navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)
            FilesPatient(newText.toInt(),Medical,ID)
        }
        composable(HomeDestinations.Menu.route) {
            MenuPatient(ID,name ,img,navegarFile = { newText ->
                navController.navigate(HomeDestinations.Files.createRoute(newText))
            },navegarCalendar = {
                navController.navigate(HomeDestinations.Calendar.route)
            })
        }


    }
}