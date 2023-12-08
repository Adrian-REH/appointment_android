package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*







@Entity(tableName = "forms", indices = [Index(value = ["_id"], unique = true)])
data class Forms(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "data") val name_last: String,
    @ColumnInfo(name = "medicals") val medical: String,
    @ColumnInfo(name = "patients") val patient: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)






@Dao
interface UserDao {


/*
    //FORMS
    @Query("SELECT * FROM forms ORDER BY id DESC")
    fun getAllForms(): LiveData<List<Forms>>
    @Delete
    fun delete(forms: Forms)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forms: Forms)
 */








}
