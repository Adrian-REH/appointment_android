package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*


class Item private constructor(name: String){

}

@Entity(tableName = "appointment", indices = [Index(value = ["_id","files"], unique = true)])
data class Appointment(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "fecha") var fecha: String,
    @ColumnInfo(name = "hora") var hora: String,
    @ColumnInfo(name = "coment") var coment: String,
    @ColumnInfo(name = "specialty") var specialty: String,
    @ColumnInfo(name = "patients") var patient: String,
    @ColumnInfo(name = "medicals") var medical: String,
    @ColumnInfo(name = "profession") var profession: String,
    @ColumnInfo(name = "files") var files: String,
    @ColumnInfo(name = "price") var price: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Dao
interface AppointmentDao {

    //APPOINTMENTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(appointment: Appointment)
    @Query("SELECT * FROM appointment ORDER BY id DESC")
    fun getAllAppointment(): LiveData<List<Appointment>>
    @Delete
    fun deleteAppointment(appointment: Appointment)
    @Query("DELETE FROM appointment ")
    fun deleteAllAppointment()


}