package app.ibiocd.appointment.presentation.components


import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.ibiocd.appointment.R
import app.ibiocd.appointment.home.navegation.HomeDestinations



@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<HomeDestinations>
) {
    val currentRoute = currentRoute(navController)
    val lists = listOf(
        painterResource(id = R.drawable.ic_home),
        painterResource(id = R.drawable.ic_favorite_border),
        painterResource(id = R.drawable.ic_appointment_calendar),
        painterResource(id = R.drawable.ic_list_box),
        painterResource(id = R.drawable.ic_menu)

    )

    BottomNavigation(
        backgroundColor = Color(1f, 1f, 1f),
        contentColor = Color(0.1f, 0.1f, 0.1f)
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon( painter = lists[screen.value], contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }

                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}