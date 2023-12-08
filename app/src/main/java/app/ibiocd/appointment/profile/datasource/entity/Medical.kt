package app.ibiocd.appointment.profile.datasource.entity

import androidx.lifecycle.LiveData
import androidx.room.*


@Entity(tableName = "medicals", indices = [Index(value = ["_id"], unique = true)])
data class Medical(
    @ColumnInfo(name = "_id") var _id: String,
    @ColumnInfo(name = "name_last") val name_last: String,
    @ColumnInfo(name = "dni") val dni: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "profession") val profession: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "direction") val direction: String,
    @ColumnInfo(name = "tuition") val tuition: String,
    @ColumnInfo(name = "img") val img: String,
    @ColumnInfo(name = "token_not") var token_not: String,
    @ColumnInfo(name = "hour_on") val hour_on: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
