package app.ibiocd.appointment.home.navegation


sealed class HomeDestinations(
    val route: String,
    val title: String,
    val value: Int
) {
    object Home: HomeDestinations("Home", "Home",0)

    object Favs: HomeDestinations("Favs/?newText={newText}", "Favorito", 1) {
        fun createRoute(newText: String) = "Favs/?newText=$newText"
    }
    object Calendar: HomeDestinations("Calendar", "Calendar", 2)
    object Files: HomeDestinations("Files/?newText={newText}", "Files", 3){
        fun createRoute(newText: String) = "Files/?newText=$newText"
    }
    object Menu: HomeDestinations("Menu/?newText={newText}", "Menu", 4){
        fun createRoute(newText: String) = "Files/?newText=$newText"
    }

    object Notification: HomeDestinations("Notification/?newText={newText}", "Notification", 5){
        fun createRoute(newText: String) = "Notification/?newText=$newText"
    }
    object SearchMedical: HomeDestinations("SearchMedical/?newText={newText}", "SearchMedical", 6){
        fun createRoute(newText: String) = "SearchMedical/?newText=$newText"
    }
    object LoadScreen: HomeDestinations("LoadScreen/?newText={newText}", "LoadScreen", 7){
        fun createRoute(newText: String) = "LoadScreen/?newText=$newText"
    }
    object Navegation: HomeDestinations("Navegation/?newText={newText}", "LoadScreen", 8){
        fun createRoute(newText: String) = "Navegation/?newText=$newText"
    }


}

