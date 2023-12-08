package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "favs", indices = [Index(value = ["_id"], unique = true)])
data class Favs(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "medicals") val medical: String,
    @ColumnInfo(name = "patients") val patient: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Dao
interface FavsDao {


    //FAVORITOS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favs: Favs)
    @Query("SELECT * FROM favs ORDER BY id DESC")
    fun getAllFavs(): LiveData<List<Favs>>
    @Delete
    fun delete(favs: Favs)

    @Query("DELETE FROM favs ")
    fun deleteAllFavs()

}