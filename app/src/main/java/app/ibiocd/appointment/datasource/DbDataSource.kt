package app.ibiocd.appointment.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.datasource.dao.MedicalDao
import app.ibiocd.appointment.profile.datasource.entity.Medical

@Database(entities = [ Appointment::class, Files::class, Patient::class, Medical::class, Forms::class, Favs::class, Specialty::class, Labs::class, Sedes::class, Date::class, Odontogram::class, Tooth::class], version =12)
abstract class DbDataSource : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun medicalDao(): MedicalDao
    abstract fun patientDao(): PatientDao
    abstract fun fileDao(): FileDao
    abstract fun favsDao(): FavsDao
    abstract fun labsDao(): LabsDao
    abstract fun specialtyDao(): SpecialtyDao
    abstract fun dateDao(): DateDao
    abstract fun sedeDao(): SedeDao
    abstract fun odontogramDao(): OdontogramDao
}