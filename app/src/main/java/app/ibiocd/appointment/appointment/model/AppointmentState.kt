package app.ibiocd.appointment.appointment.model

import app.ibiocd.appointment.model.AppointmentRespons

data class AppointmentState(
    val isLoading: Boolean = false,
    val isSuccess: AppointmentRespons = AppointmentRespons("","","","","","","","","",""),
    val isError: String? = ""
)
data class LoadMedicalAppointmentState(
    val isLoading: Boolean = false,
    val isSuccess: AppointmentRespons = AppointmentRespons("","","","","","","","","",""),
    val isError: String? = ""
)