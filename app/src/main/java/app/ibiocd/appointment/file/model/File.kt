package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Entity(tableName = "files", indices = [Index(value = ["_id"], unique = true)])
data class Files(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "laboratory") val laboratory: String,
    @ColumnInfo(name = "prescriptions") val prescriptions: String,
    @ColumnInfo(name = "stadies") val stadies: String,
    @ColumnInfo(name = "odontogram") var odontogram: String,
    @ColumnInfo(name = "form") val form: String,
    @ColumnInfo(name = "patients") val patient: String,
    @ColumnInfo(name = "medicals") val medical: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Dao
interface FileDao {


    //FILES
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertfile(files: Files)
    @Query("SELECT * FROM files ORDER BY id DESC")
    fun getAllFile(): LiveData<List<Files>>
    @Delete
    fun deleteFile(files: Files)
    @Query("DELETE FROM files ")
    fun deleteAllFiles()

}