package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Entity(tableName = "specialty", indices = [Index(value = ["_id"], unique = true)])
data class Specialty(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "comment") var comment: String,
    @ColumnInfo(name = "medicals") val medical: String,
    @ColumnInfo(name = "offer") var offer: String,
    @ColumnInfo(name = "price") var price: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Dao
interface SpecialtyDao {


    //SPECIALTY
    @Query("SELECT * FROM specialty ORDER BY id DESC")
    fun getAllSpecialty(): LiveData<List<Specialty>>
    @Delete
    fun delete(specialty: Specialty)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(specialty: Specialty)

}