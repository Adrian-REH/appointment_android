package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*
@Entity(tableName = "labs", indices = [Index(value = ["_id"], unique = true)])
data class Labs(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "name_lab") val name_lab: String,
    @ColumnInfo(name = "telefono") val telefono: String,
    @ColumnInfo(name = "correo") val correo: String,
    @ColumnInfo(name = "acreditado") val acreditado: String,
    @ColumnInfo(name = "hour_on") val hour_on: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
@Dao
interface LabsDao {


    //LABS
    @Query("SELECT * FROM labs ORDER BY id DESC")
    fun getAllLabs(): LiveData<List<Labs>>
    @Delete
    fun delete(labs: Labs)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(labs: Labs)

}