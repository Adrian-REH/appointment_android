package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "dates", indices = [Index(value = ["_id"], unique = true)])
data class Date(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "medicals") val medical: String,
    @ColumnInfo(name = "lunes") var lunes: String,
    @ColumnInfo(name = "martes") var martes: String,
    @ColumnInfo(name = "miercoles") var miercoles: String,
    @ColumnInfo(name = "jueves") var jueves: String,
    @ColumnInfo(name = "viernes") var viernes: String,
    @ColumnInfo(name = "sabado") var sabado: String,
    @ColumnInfo(name = "domingo") var domingo: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0


)

@Dao
interface DateDao {

    //DATES
    @Query("SELECT * FROM dates ORDER BY id DESC")
    fun getAllDate(): LiveData<List<Date>>
    @Delete
    fun delete(date: Date)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(date: Date)
}