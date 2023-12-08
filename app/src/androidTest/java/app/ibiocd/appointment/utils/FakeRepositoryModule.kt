package app.ibiocd.appointment.utils

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.ibiocd.appointment.auth.di.AppModule
import app.ibiocd.appointment.auth.model.AuthRepository
import app.ibiocd.appointment.di.RepositoryModule
import app.ibiocd.appointment.file.model.FileRepository
import app.ibiocd.appointment.model.*
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.profile.model.repository.DateRepository
import app.ibiocd.appointment.profile.model.repository.MedicalRepository
import app.ibiocd.appointment.profile.model.repository.PatientRepository
import app.ibiocd.appointment.profile.model.repository.SpecialtyRepository
import app.ibiocd.appointment.util.ApiResource
import app.ibiocd.appointment.util.Constants
import app.ibiocd.appointment.util.Resource
import com.nopalsoft.simple.rest.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class, AppModule::class]
)
class FakeRepositoryModule {

    @Provides
    @Singleton
    fun odontogramRepository(): OdontogramRepository =
        object : OdontogramRepository {
            private val tooth = MutableLiveData<List<Tooth>>(listOf())
            private val odontogram = MutableLiveData<List<Odontogram>>(listOf())

            override suspend fun putOdontogram(
                id: String,
                data: ApiTooth,
                patient: String,
                medical: String
            ): String {
                return ""
            }

            override suspend fun postOdontogram(
                data: ApiTooth,
                patient: String,
                medical: String
            ): Flow<ApiResource<OdontogramResponse>> {
                return flow<ApiResource<OdontogramResponse>> {

                    emit(ApiResource.Loading())


                }.catch {
                    emit(ApiResource.Error(it.message.toString()))

                }
            }

            override suspend fun deleteOdontogram(toDelete: Odontogram) {

            }

            override suspend fun deleteAllOdontogram() {

            }

            override suspend fun getOdontogram(odontogramID: String) {

            }

            override fun getAllOdontogram(): LiveData<List<Odontogram>> {
                return  odontogram

            }

            override fun getAllTooth(): LiveData<List<Tooth>> {

                return  tooth
            }


        }

    @Provides
    @Singleton
    fun appointmentRepository(): AppointmetRepository =
        object : AppointmetRepository {

            private val appointmets = MutableLiveData<List<Appointment>>(listOf())


            fun appointmentfun():ApiResource<AppointmentRespons>{
                return ApiResource.Success(AppointmentRespons("","","","","","","","","",""))
            }

            override suspend fun deleteAppointment(toDelete: Appointment) {
                appointmets.postValue(appointmets.value?.toMutableList()?.apply { remove(toDelete) })
            }

            override fun getAllAppointmentDao(): LiveData<List<Appointment>> {

                val listAppointment= ArrayList<Appointment>()
                val NewAppointment = Appointment("IDa ${appointmets.value!!.size}", "FECHA", "11:00", "COMENT","IDs 1","IDp 0","IDm 0",Constants.PROFESSION,"IDf 1","100")

                listAppointment.add(NewAppointment)
                appointmets.postValue(appointmets.value?.toMutableList()?.apply { add(NewAppointment) })

                return  appointmets
            }


            override suspend fun getPatientAppointment(patient: String): ArrayList<Appointment> {
                val listAppointment= ArrayList<Appointment>()
                val NewAppointment = Appointment("IDa ${appointmets.value!!.size}", "FECHA", "11:00", "COMENT","IDs 1","IDp 0","IDm 0",Constants.PROFESSION,"IDf 1","100")

                listAppointment.add(NewAppointment)
                appointmets.postValue(appointmets.value?.toMutableList()?.apply { add(NewAppointment) })

                return listAppointment
            }

            override suspend fun getMedicalAppointment(medical:String): ArrayList<Appointment> {

                val listAppointment= ArrayList<Appointment>()
                val NewAppointment = Appointment("IDa ${appointmets.value!!.size}", "FECHA", "11:00", "COMENT","IDs 1","IDp 0","IDm 0",Constants.PROFESSION,"IDf 1","100")

                listAppointment.add(NewAppointment)
                appointmets.postValue(appointmets.value?.toMutableList()?.apply { add(NewAppointment) })

                return listAppointment
            }


            override suspend fun deleteAllAppointment() {
                appointmets.postValue(appointmets.value?.toMutableList()?.apply { removeAll(appointmets.value!!) })

            }

            override suspend fun postAppointment(appointment: Appointment): Flow<ApiResource<AppointmentRespons>> {
                return flow {

                    emit(ApiResource.Loading())
                    val result = appointmentfun()
                    emit(ApiResource.Success(result.result!!))

                }.catch {
                    emit(ApiResource.Error(message = "asdf"))
                }
            }
        }

    @Provides
    @Singleton
    fun userRepository(): UserRepository =
        object : UserRepository {

            override suspend fun uploadImage(
                context: Activity,
                IMAGENAME: String,
                imageData: ByteArray
            ): String {
                TODO("Not yet implemented")
            }

        }

    @Provides
    @Singleton
    fun favRepository():FavRepository = object :FavRepository{
        private val favs = MutableLiveData<List<Favs>>(listOf())


        //FAVS
        override fun getAllFavDao(): LiveData<List<Favs>> {

            if (favs.value!!.isEmpty()){
                val NewFavs = Favs("IDf ${favs.value!!.size}", "IDm ${favs.value!!.size}", "IDp ${favs.value!!.size}")

                favs.postValue(favs.value?.toMutableList()?.apply { add(NewFavs) })

            }

            return favs
        }
        override suspend fun getPatientFavs(medical: String) {

            if (favs.value!!.isEmpty()){
                val user1 = Favs("IDr ${favs.value!!.size}", "IDm 0", "IDp ${favs.value!!.size}")

                favs.postValue(favs.value?.toMutableList()?.apply { add(user1) })

            }
        }
        override suspend fun getMedicalFavs(patient: String) {

            if (favs.value!!.isEmpty()){
                val user1 = Favs("IDr ${favs.value!!.size}", "IDm ${favs.value!!.size}", "IDp 0")

                favs.postValue(favs.value?.toMutableList()?.apply { add(user1) })

            }
        }
        override suspend fun postFavs(favs: Favs) {

        }
        override suspend fun deleteFavs(toDelete: Favs) {

        }
        override suspend fun deleteAllFavs() {
            favs.postValue((favs.value?.toMutableList()?.apply { removeAll(favs.value!!) }))

        }

    }

    @Provides
    @Singleton
    fun labRepository():LabRepository = object :LabRepository{
        private val lab = MutableLiveData<List<Labs>>(listOf())

        //Lab
        override suspend fun getLabs() {

        }
        override fun getAllLabs(): LiveData<List<Labs>> {

            if (lab.value!!.isEmpty()){
                val NewLabs = Labs("IDm ${lab.value!!.size}", "Name ${lab.value!!.size} Lab ${lab.value!!.size}", "TEL","EMAIL","","HOUR_ON 1")

                lab.postValue(lab.value?.toMutableList()?.apply { add(NewLabs) })

            }
            return lab
        }

    }

    @Provides
    @Singleton
    fun sedeRepository():SedeRepository = object :SedeRepository{
        private val sede = MutableLiveData<List<Sedes>>(listOf())
        private val lab = MutableLiveData<List<Labs>>(listOf())

        //SEDE
        override fun getAllSedes(): LiveData<List<Sedes>> {
            if (sede.value!!.isEmpty()){
                val NewLabs = Sedes("IDs ${sede.value!!.size}", "Name ${sede.value!!.size} Sede ${lab.value!!.size}", "","IDm 0")

                sede.postValue(sede.value?.toMutableList()?.apply { add(NewLabs) })

            }
            return sede
        }
        override suspend fun getSede() {

        }

    }
    @Provides
    @Singleton
    fun specialtyRepository(): SpecialtyRepository= object : SpecialtyRepository{
        private val specialty = MutableLiveData<List<Specialty>>(listOf())

        //SPECIALTY
        override fun getAllSpecialty(): LiveData<List<Specialty>> {
            if (specialty.value!!.isEmpty()){
                val NewSpecialty = Specialty("IDf ${specialty.value!!.size}", Constants.SPECIALTY, "COMMENT","IDm 0","","100")

                specialty.postValue(specialty.value?.toMutableList()?.apply { add(NewSpecialty) })

            }

            return specialty
        }

        override suspend fun getSpecialty(): ArrayList<Specialty> {
            val listSpecialty= ArrayList<Specialty>()
            val NewSpecialty = Specialty("IDf ${specialty.value!!.size}", Constants.SPECIALTY, "COMMENT","IDm 0","","100")

            listSpecialty.add(NewSpecialty)
            specialty.postValue(specialty.value?.toMutableList()?.apply { add(NewSpecialty) })

            return listSpecialty


        }

        override suspend fun postSpecialty(specialty: Specialty) {

        }

        override suspend fun putSpecialty(specialty: Specialty) {

        }

        override suspend fun deleteSpecialty(specialty: Specialty) {

        }
    }
    @Provides
    @Singleton
    fun fileRepository(): FileRepository= object : FileRepository{
        private val file = MutableLiveData<List<Files>>(listOf())


        //FILES
        override fun getAllFile(): LiveData<List<Files>> {


            return file
        }

        override suspend fun deleteFile(toDelete: Files) {

        }

        override suspend fun getPatientFiles(patient: String) {

        }

        override suspend fun getMedicalFiles(medical: String) {
            val listFile= ArrayList<Files>()
            val NewFile = Files("ID ${file.value!!.size}", "Name 0 Lab 0", "PRESC ${file.value!!.size}", "STUDY ${file.value!!.size}","ODONT","FORM ${file.value!!.size}","IDp 0","IDm 0")

            listFile.add(NewFile)
            file.postValue(file.value?.toMutableList()?.apply { add(NewFile) })

        }

        override suspend fun postFile(files: Files): String {
            file.postValue(file.value?.toMutableList()?.apply { add(files) })
            return ""
        }

        override suspend fun putFile(files: Files) {

        }

        override suspend fun deleteAllFiles() {

            file.postValue((file.value?.toMutableList()?.apply { removeAll(file.value!!) }))
        }

    }
    @Provides
    @Singleton
    fun dateRepository(): DateRepository = object : DateRepository{
        //DATE
        private val date = MutableLiveData<List<Date>>(listOf())

        override suspend fun getDate(): ArrayList<Date> {
            val listDate= ArrayList<Date>()
            val NewDate = Date("HOUR_ON 1", "IDm 1", "10:00 de 16:00", "10:00 de 16:00","10:00 de 16:00","10:00 de 16:00","10:00 de 16:00","10:00 de 16:00","10:00 de 16:00")

            listDate.add(NewDate)
            date.postValue(date.value?.toMutableList()?.apply { add(NewDate) })

            return listDate


        }
        override fun getAllDate(): LiveData<List<Date>> {
            if (date.value!!.isEmpty()){
                val NewDate = Date("HOUR_ON 1", "IDm 1", "10:00 de 16:00", "10:00 de 16:00","10:00 de 16:00","10:00 de 16:00","10:00 de 16:00","10:00 de 16:00","10:00 de 16:00")

                date.postValue(date.value?.toMutableList()?.apply { add(NewDate) })

            }
            return date

        }
        override suspend fun deleteDate(toDelete: Date) {

        }

        override suspend fun putDate(date: Date) {

        }

        override suspend fun postDate(date: Date) {

        }

    }
    @Provides
    @Singleton
    fun patientRepository(): PatientRepository =
        object : PatientRepository {
            private val patient = MutableLiveData<List<Patient>>(listOf())

            override suspend fun putPatient(patient: Patient): String {

                return ""
            }

            override suspend fun deleteAllPatient() {
                patient.postValue(patient.value?.toMutableList()?.apply { removeAll(patient.value!!) })
            }

            override suspend fun getByUsername(user: String): Patient {
                val NewPatient = Patient("IDp ${patient.value!!.size}", "NAME ${patient.value!!.size} LAST ${patient.value!!.size}", "DNI ${patient.value!!.size}", "PHONE ${patient.value!!.size}","EMAIL","DIR ${patient.value!!.size}","IMG ${patient.value!!.size}","")

                patient.postValue(patient.value?.toMutableList()?.apply { add(NewPatient) })

                return NewPatient

            }


            override suspend fun postPatient(
                name_last: String,
                dni: String,
                email: String,
                token: String
            ): String {
                return ""
            }



            override suspend fun getAllPatient(): ArrayList<Patient> {
                val listPatient= ArrayList<Patient>()
                val NewPatient = Patient("IDp ${patient.value!!.size}", "NAME ${patient.value!!.size} LAST ${patient.value!!.size}", "DNI ${patient.value!!.size}", "PHONE ${patient.value!!.size}","EMAIL","DIR ${patient.value!!.size}","IMG ${patient.value!!.size}","")

                listPatient.add(NewPatient)
                patient.postValue(patient.value?.toMutableList()?.apply { add(NewPatient) })

                return listPatient
            }

            override fun getAllPatientDao(): LiveData<List<Patient>> {
                if (patient.value!!.isEmpty()){
                    val NewPatient = Patient("IDp ${patient.value!!.size}", "NAME ${patient.value!!.size} LAST ${patient.value!!.size}", "DNI ${patient.value!!.size}", "PHONE ${patient.value!!.size}","EMAIL","DIR ${patient.value!!.size}","IMG ${patient.value!!.size}","")


                    patient.postValue(patient.value?.toMutableList()?.apply { add(NewPatient) })

                }
                return patient
            }

            override suspend fun deletePatient(toDelete: Patient) {
                patient.postValue(patient.value?.toMutableList()?.apply { remove(toDelete) })
            }


        }
    @Provides
    @Singleton
    fun medicalRepository(): MedicalRepository =
        object : MedicalRepository {

            private val medical = MutableLiveData<List<Medical>>(listOf())


            override suspend fun postMedical(
                name_last: String,
                dni: String,
                phone: String,
                profession: String,
                email: String,
                direccion: String,
                tuition: String,
                token: String
            ): String {
return ""
            }

            override suspend fun getMedical() {
                val NewMedical = Medical("IDm ${medical.value!!.size}", "Name ${medical.value!!.size} Last ${medical.value!!.size}", "DNI ${medical.value!!.size}", "PHONE ${medical.value!!.size}",Constants.PROFESSION,"EMAIL","DIR","MATRICULA","IMG","","HOUR_ON 1")

                medical.postValue(medical.value?.toMutableList()?.apply { add(NewMedical) })

            }

            override fun getAllMedical(): LiveData<List<Medical>> {

                    val NewMedical = Medical("IDm ${medical.value!!.size}", "Name ${medical.value!!.size} Last ${medical.value!!.size}", "DNI ${medical.value!!.size}", "PHONE ${medical.value!!.size}",Constants.PROFESSION,"EMAIL","DIR","MATRICULA","IMG","","HOUR_ON 1")

                    medical.postValue(medical.value?.toMutableList()?.apply { add(NewMedical) })


                return medical
            }

            override suspend fun getByUsername(user: String): Medical {
                val NewMedical = Medical("IDm ${medical.value!!.size}", "Name ${medical.value!!.size} Last ${medical.value!!.size}", "DNI ${medical.value!!.size}", "PHONE ${medical.value!!.size}",Constants.PROFESSION,"EMAIL","DIR","MATRICULA","IMG","","HOUR_ON 1")
                medical.postValue(medical.value?.toMutableList()?.apply { add(NewMedical) })

                return NewMedical
            }


            override suspend fun deleteAllMedical() {
                medical.postValue(medical.value?.toMutableList()?.apply { removeAll(medical.value!!) })

            }

            override suspend fun putMedical(medical: Medical): String {

                return ""
            }

            override suspend fun getDirectionLatLng(
                address: String,
                neighbourhood: String,
                country: String
            ): List<Double> {
                TODO("Not yet implemented")
            }

            override fun getMedicalById(id: String): LiveData<Medical> {
                TODO("Not yet implemented")
            }


        }


    @Provides
    @Singleton
    fun authRepository(): AuthRepository =
        object : AuthRepository{
            override fun loginUser(email: String, password: String): Flow<Resource<String>> {
                return flow<Resource<String>> {
                    emit(Resource.Loading())
                    delay(300)
                    emit(Resource.Success("Success"))

                }.catch {
                    emit(Resource.Error(it.message.toString()))

                }
            }

            override fun registerUser(email: String, password: String): Flow<Resource<String>> {
                TODO("Not yet implemented")
            }

            override fun verifyUser() {
                TODO("Not yet implemented")
            }

            override fun resetUser(email: String): Flow<Resource<Void>> {
                TODO("Not yet implemented")
            }

        }


}