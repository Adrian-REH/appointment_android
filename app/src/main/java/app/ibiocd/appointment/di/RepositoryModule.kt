package app.ibiocd.appointment.di

import app.ibiocd.appointment.auth.model.AuthRepository
import app.ibiocd.appointment.auth.model.AuthRepositoryImpl
import app.ibiocd.appointment.profile.model.repository.DateRepository
import app.ibiocd.appointment.profile.model.repository.*
import app.ibiocd.appointment.file.model.FileRepository
import app.ibiocd.appointment.file.model.FileRepositoryImpl
import com.nopalsoft.simple.rest.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun sedeRepository(repo: SedeRepositoryImp): SedeRepository
    @Binds
    @Singleton
    abstract fun labRepository(repo: LabRepositoryImp): LabRepository
    @Binds
    @Singleton
    abstract fun favRepository(repo: FavRepositoryImp): FavRepository
    @Binds
    @Singleton
    abstract fun fileRepository(repo: FileRepositoryImpl): FileRepository
    @Binds
    @Singleton
    abstract fun dateRepository(repo: DateRepositoryImpl): DateRepository
    @Binds
    @Singleton
    abstract fun specialtyRepository(repo: SpecialtyRepositoryImp): SpecialtyRepository
    @Binds
    @Singleton
    abstract fun userRepository(repo: UserRepositoryImp): UserRepository
    @Binds
    @Singleton
    abstract fun appointmentRepository(repo: AppointmetRepositoryImp): AppointmetRepository
    @Binds
    @Singleton
    abstract fun patientRepository(repo: PatientRepositoryImp): PatientRepository
    @Binds
    @Singleton
    abstract fun medicalRepository(repo: MedicalRepositoryImp): MedicalRepository
    @Binds
    @Singleton
    abstract fun odontogramRepository(repo: OdontogramRepositoryImp): OdontogramRepository
    @Binds
    @Singleton
    abstract fun notificationRepository(repo: NotificationRepositoryImp): NotificationRepository


}








