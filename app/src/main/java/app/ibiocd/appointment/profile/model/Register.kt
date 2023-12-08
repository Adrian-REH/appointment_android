package app.ibiocd.appointment.model

import androidx.lifecycle.LiveData
import androidx.room.*


/**VERIFICA SI EL MEDICO ESTA REGISTRADO **/
@Entity(tableName = "register", indices = [Index(value = ["_id"], unique = true)])
data class Register(
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "medicals") val medical: String,
    @ColumnInfo(name = "patients") val patient: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

