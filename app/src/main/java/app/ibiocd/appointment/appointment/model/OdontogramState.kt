package app.ibiocd.appointment.appointment.model

import app.ibiocd.appointment.model.OdontogramResponse

data class OdontogramState(
    val isLoading: Boolean = false,
    val isSuccess: OdontogramResponse = OdontogramResponse("", emptyList(),"",""),
    val isError: String? = ""
)