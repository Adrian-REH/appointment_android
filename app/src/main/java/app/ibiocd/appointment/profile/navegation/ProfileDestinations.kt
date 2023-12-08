package app.ibiocd.appointment.profile.navegation


sealed class ProfileDestinations(
    val route: String,
    val title: String,
    val value: Int
) {
    object ResumenPatient: ProfileDestinations("ResumenPatient/?newText=", "Beginning",0){
        fun createRoute() = "ResumenPatient/?newText="
    }
    object EditPatient: ProfileDestinations("EditPatient/?newText={newText}", "Check In", 1) {
        fun createRoute() = "EditPatient/?newText="
    }
    object ResumenMedical: ProfileDestinations("ResumenMedical/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResumenMedical/?newText="
    }
    object EditMedical: ProfileDestinations("EditMedical/?newText={newText}", "Log In", 2){
        fun createRoute() = "EditMedical/?newText="
    }
    object ResetName: ProfileDestinations("ResetName/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResetName/?newText="
    }
    object ResetDNI: ProfileDestinations("ResetDNI/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResetDNI/?newText="
    }
    object ResetImage: ProfileDestinations("ResetImage/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResetImage/?newText="
    }
    object ResetPhone: ProfileDestinations("ResetPhone/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResetPhone/?newText="
    }
    object ResetTuition: ProfileDestinations("ResetUtilits/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResetUtilits/?newText="
    }
    object ResetLocation: ProfileDestinations("ResetLocation/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResetLocation/?newText="
    }
    object ResetEmail: ProfileDestinations("ResetEmail/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResetEmail/?newText="
    }
    object ResetProfession: ProfileDestinations("ResetProfession/?newText={newText}", "Log In", 2){
        fun createRoute() = "ResetProfession/?newText="
    }
    object ListSchedules: ProfileDestinations("ListSchedules/?newText={newText}", "Log In", 2){
        fun createRoute() = "ListSchedules/?newText="
    }
    object ListOffer: ProfileDestinations("ListOffer/?newText={newText}", "Log In", 2){
        fun createRoute() = "ListOffer/?newText="
    }
    object ListSpecialty: ProfileDestinations("ListSpecialty/?newText={newText}", "Log In", 2){
        fun createRoute() = "ListSpecialty/?newText="
    }

}

