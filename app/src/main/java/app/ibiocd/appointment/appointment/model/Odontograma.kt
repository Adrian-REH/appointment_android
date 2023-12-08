package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field


@Entity(tableName = "odontogram", indices = [Index(value = ["_id"], unique = true)])
data class Odontogram(
    @ColumnInfo(name = "_id") var _id: String,
    @ColumnInfo(name = "data") val data: String,
    @ColumnInfo(name = "medicals") var medical: String,
    @ColumnInfo(name = "patients") var patient: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
@Entity(tableName = "tooth", indices = [Index(value = ["number"], unique = true)])
data class Tooth(
    @SerializedName("_id") var _id:String,
    @SerializedName("number") var number:String,
    @SerializedName("imgTop") var imgTop:String,
    @SerializedName("imgBot") var imgBot:String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0

)
data class Tooths(
     var number:String,
    var imgTop:String,
    var imgBot:String
)
@Dao
interface OdontogramDao {


    //ODONTOGRAM

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tooth: Tooth)
    @Query("SELECT * FROM tooth ORDER BY id DESC")
    fun getAllTooth(): LiveData<List<Tooth>>
    @Delete
    fun delete(tooth: Tooth)
    @Query("DELETE FROM tooth ")
    fun deleteAllTooth()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(odontogram: Odontogram)
    @Query("SELECT * FROM odontogram ORDER BY id DESC")
    fun getAllOdontogram(): LiveData<List<Odontogram>>
    @Delete
    fun delete(odontogram: Odontogram)
    @Query("DELETE FROM odontogram ")
    fun deleteAllOdontogram()


}