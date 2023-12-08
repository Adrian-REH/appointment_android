package app.ibiocd.appointment.auth.navegation


sealed class SessionDestinationsActivity(
    val route: String,
    val title: String,
    val value: Int
) {
    object Beginning: SessionDestinationsActivity("Beginning/?newText=", "Beginning",0){
        fun createRoute() = "Beginning/?newText="
    }
    object CheckIn: SessionDestinationsActivity("CheckIn/?newText={newText}", "Check In", 1) {
        fun createRoute() = "CheckIn/?newText="
    }
    object LogIn: SessionDestinationsActivity("LogIn/?newText={newText}", "Log In", 2){
        fun createRoute() = "LogIn/?newText="
    }
    object ResetPassword: SessionDestinationsActivity("ResetPassword/?newText={newText}", "Reset Password", 3){
        fun createRoute() = "ResetPassword/?newText="
    }
}

