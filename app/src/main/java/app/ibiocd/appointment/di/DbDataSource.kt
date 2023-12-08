package app.ibiocd.appointment.di

import android.content.Context
import androidx.room.Room
import app.ibiocd.appointment.datasource.DbDataSource
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.datasource.dao.MedicalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbDataSource {


    //base de datos local
    @Singleton
    @Provides
    fun dbDataSource(@ApplicationContext context: Context): DbDataSource {
        return Room.databaseBuilder(context, DbDataSource::class.java, "appointment_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun userDao(db: DbDataSource): UserDao = db.userDao()
    @Singleton
    @Provides
    fun appointmentDao(db: DbDataSource): AppointmentDao = db.appointmentDao()
    @Singleton
    @Provides
    fun odontogramDao(db: DbDataSource): OdontogramDao = db.odontogramDao()
    @Singleton
    @Provides
    fun medicalDao(db: DbDataSource): MedicalDao = db.medicalDao()

    @Singleton
    @Provides
    fun patientDao(db: DbDataSource): PatientDao = db.patientDao()

    @Singleton
    @Provides
    fun fileDao(db: DbDataSource): FileDao = db.fileDao()


    @Singleton
    @Provides
    fun favsDao(db: DbDataSource): FavsDao = db.favsDao()

    @Singleton
    @Provides
    fun labsDao(db: DbDataSource): LabsDao = db.labsDao()
    @Singleton
    @Provides
    fun specialtyDao(db: DbDataSource): SpecialtyDao = db.specialtyDao()

    @Singleton
    @Provides
    fun dateDao(db: DbDataSource): DateDao = db.dateDao()
    @Singleton
    @Provides
    fun sedeDao(db: DbDataSource): SedeDao = db.sedeDao()


}