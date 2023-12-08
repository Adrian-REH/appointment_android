package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Entity(tableName = "patients", indices = [Index(value = ["_id"], unique = true)])
data class Patient(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "name_last") val name_last: String,
    @ColumnInfo(name = "dni") val dni: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "direction") val direction: String,
    @ColumnInfo(name = "img") val img: String,
    @ColumnInfo(name = "token_not") var token_not: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Dao
interface PatientDao {


    //PATIENTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(patient: Patient)
    @Query("SELECT * FROM patients ORDER BY id DESC")
    fun getAllPatient(): LiveData<List<Patient>>
    @Delete
    fun delete(patient: Patient)
    @Query("DELETE FROM patients ")
    fun deleteAllPatient()

}