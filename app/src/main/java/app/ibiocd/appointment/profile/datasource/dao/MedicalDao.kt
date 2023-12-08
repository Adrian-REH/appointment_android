package app.ibiocd.appointment.profile.datasource.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import app.ibiocd.appointment.profile.datasource.entity.Medical


@Dao
interface MedicalDao {


    //TODO no deberia guardar tantos medicos...
    @Query("SELECT * FROM medicals ORDER BY id DESC")
    fun getAllMedical(): LiveData<List<Medical>>

    @Query("SELECT * FROM medicals WHERE id = :medicalId")
    fun getByIdMedical(medicalId: String): LiveData<Medical>

    @Delete
    fun delete(medical: Medical)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(medical: Medical)

    @Query("DELETE FROM medicals ")
    fun deleteAllMedical()


}