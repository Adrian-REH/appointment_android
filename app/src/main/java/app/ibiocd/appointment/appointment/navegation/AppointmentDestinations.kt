package app.ibiocd.appointment.appointment.navegation


sealed class AppointmentDestinations(
    val route: String,
    val title: String,
    val value: Int
) {
    object HomeSelect: AppointmentDestinations("HomeSelect/?newText=", "Beginning",0){
        fun createRoute() = "HomeSelect/?newText="
    }
    object AppointmentCreate: AppointmentDestinations("AppointmentCreate/?newText=", "Beginning",0){
        fun createRoute() = "AppointmentCreate/?newText="
    }
    object AppointmentLabCreate: AppointmentDestinations("AppointmentLabCreate/?newText={newText}", "Check In", 1) {
        fun createRoute() = "AppointmentLabCreate/?newText="
    }
    object AppointmentPatient: AppointmentDestinations("AppointmentPatient/?newText={newText}", "Log In", 2){
        fun createRoute() = "AppointmentPatient/?newText="
    }
    object AppointmentMedical: AppointmentDestinations("AppointmentMedical/?newText={newText}", "Log In", 2){
        fun createRoute() = "AppointmentMedical/?newText="
    }
    object AppointmentLab: AppointmentDestinations("AppointmentLab/?newText={newText}", "Log In", 2){
        fun createRoute() = "AppointmentLab/?newText="
    }
    object Odontogram: AppointmentDestinations("Odontogram/?newText={newText}", "Log In", 2){
        fun createRoute() = "Odontogram/?newText="
    }
}

