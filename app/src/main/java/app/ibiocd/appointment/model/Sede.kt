package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "sedes", indices = [Index(value = ["_id"], unique = true)])
data class Sedes(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "name_sede") val name_sede: String,
    @ColumnInfo(name = "direction") val direction: String,
    @ColumnInfo(name = "laboratory") val laboratory: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Dao
interface SedeDao {

    //SEDES
    @Query("SELECT * FROM sedes ORDER BY id DESC")
    fun getAllSedes(): LiveData<List<Sedes>>
    @Delete
    fun delete(sedes: Sedes)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sedes: Sedes)

}