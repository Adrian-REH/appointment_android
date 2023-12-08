package app.ibiocd.appointment.util
class Constants {

    companion object {
        const val BASE_URL = "https://fcm.googleapis.com"
        const val SERVER_KEY = "AAAAVMnpm9c:APA91bFmtB6m4bdfGZ3RolBLAmlzt4JDOcgCAz8l6CI7BEXWjqGrmyDpkohTKPtTePhfL9rhXU2PGmX4FCOv_S0dkwKYKj-0kcO0jWAMtrDp5ibjAuzpQSgvhyCfbLhjsPJUQ5CF8mpT"
        const val CONTENT_TYPE = "application/json"
        const val USER_EMAIL = "adrianherrera.r.e@gmail.com"
        const val USER_PASSWORD = "dexter"
        const val TOKEN = ""
        const val PATIENT = "PACIENTE"
        const val MEDICAL = "MEDICAL"
        const val CONFIRM = "CONFIRMAR"
        const val CONTINUE = "Continuar"
        const val SEARCH = "Search"
        const val SPECIALTY = "Ortodoncia"
        const val PROFESSION = "Odontologo"
        const val MEDICAL_NAME = "Name 0 Last 0"
        const val PATIENT_NAME = "NAME 0 LAST 0"
        const val HOUR = "10:00"




    }
}
class SessionScreen {
    companion object {
        //component Text

        const val COMP_LOGIN = "Iniciar sesion"
        const val DESCRIPTION_IC_OFF_PASS = "Ocultar contraseña"
        const val DESCRIPTION_IC_ON_PASS = "Revelar contraseña"
        //component KEY

        const val TAG_TEXT_USER = "user_session"
        const val TAG_TEXT_PASSWORD = "pass_session"
        const val TAG_IC_PASSWORD = "ic_visibility"

    }

}
class HomeConstants{
    companion object {
        //component Text

        const val COMP_LOGIN = "card_new_appointment"
        //component KEY

        const val TAG_CARD_NEW_APPOINTMENT = "card_new_appointment"
        const val TAG_BTN_NEW_APPOINTMENT = "btn_new_appointment"

    }

}
class AppointmentConstant {
    companion object {
        //component Text

        const val DESCRIPTION_LIST_MEDICAL = "MedicalList"
        const val DESCRIPTION_LIST_PROFESSION = "ProfessionList"
        const val DESCRIPTION_LIST_SPECIALITY = "SpecialtyList"
        const val DESCRIPTION_LIST_PATIENT= "PatientList"
        const val DESCRIPTION_BTN_CALENDAR= "CalendarSee"
        //component KEY

        const val TAG_CARD_NEW_APPOINTMENT = "card_new_appointment"
        const val TAG_BTN_NEW_APPOINTMENT = "btn_new_appointment"

    }

}
