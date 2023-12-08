package app.ibiocd.appointment


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.ibiocd.appointment.appointment.datasource.AppointmentDataSource
import app.ibiocd.appointment.model.Appointment
import app.ibiocd.appointment.model.AppointmentDao
import com.nopalsoft.simple.rest.repository.AppointmetRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val appointment1 = Appointment("63ef0b052a8d7fc1f582dda4", "18022023", "09:00", "","Sangre","639e5b3678539b79cd8dfbe1","639e34145bb5f9cceeaa8a6a","corriente 121","63ef0ac82a8d7fc1f582dda2","")
private val appointment2 = Appointment("63ef0c002a8d7fc1f582de2c", "20022023", "09:15", "","Sangre","639e5b3678539b79cd8dfbe1","639e34145bb5f9cceeaa8a6a","corriente 121","63ef0beb2a8d7fc1f582de2a","")
private val appointment3 = Appointment("63ef9f83af0d549f0faa3034", "19022023", "09:15", "","Sangre","639e5b3678539b79cd8dfbe1","63e98c3b9159d413432d42ec","corriente 121","63ef9f7aaf0d549f0faa3032","")


class AppointmentRepositoryTest {

    private lateinit var newsRepository: AppointmetRepositoryImp
    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: AppointmentDataSource

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    @Before
    fun createService(){
        Dispatchers.setMain(mainThreadSurrogate)


        mockWebServer = MockWebServer() .apply {
            dispatcher=myDispatcher }

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppointmentDataSource::class.java)

        newsRepository = AppointmetRepositoryImp(service, MockAppointmentDao())


    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @After
    fun tearDown() {
        mockWebServer.shutdown()

        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Appointment on the DB are retrieved correctly`(){
        val appointments = newsRepository.getAllAppointmentDao()
        Assert.assertEquals(3,appointments.value?.size)

    }
    @Test
    fun `Appointment is deleted correctly`(){
        runBlocking {
             newsRepository.deleteAppointment(appointment1)

            val appointments = newsRepository.getAllAppointmentDao()
            Assert.assertEquals(2,appointments.value?.size)

        }
    }
    @Test
    fun `Appointment is deleted all correctly`(){
        runBlocking {
             newsRepository.deleteAllAppointment()

            val appointments = newsRepository.getAllAppointmentDao()
            Assert.assertEquals(0,appointments.value?.size)

        }
    }

    @Test
    fun `Appointment medical is fetched  correctly`() =
        runBlocking(Dispatchers.Main) {


            val data = service.getMedicalturno("639e34145bb5f9cceeaa8a6a")
            val medicalResponse = newsRepository.getMedicalAppointment("639e34145bb5f9cceeaa8a6a")

            var request = mockWebServer.takeRequest()
            val result =(data).result


            MatcherAssert.assertThat(request.path,CoreMatchers.`is`("/appointment/medicals/639e34145bb5f9cceeaa8a6a"))
            MatcherAssert.assertThat(request.method,CoreMatchers.`is`("GET"))

            MatcherAssert.assertThat(result.size,CoreMatchers.`is`(3))
            MatcherAssert.assertThat(medicalResponse.size,CoreMatchers.`is`(3))

            val firstAppointment  = result[0]
            Assert.assertEquals(firstAppointment._id,"63ef0b052a8d7fc1f582dda4")
            Assert.assertEquals(firstAppointment.fecha,"18022023")
            Assert.assertEquals(firstAppointment.hora,"09:00")
            Assert.assertEquals(firstAppointment.coment,"")
            Assert.assertEquals(firstAppointment.specialty,"Sangre")
            Assert.assertEquals(firstAppointment.patient,"639e5b3678539b79cd8dfbe1")
            Assert.assertEquals(firstAppointment.medical,"639e34145bb5f9cceeaa8a6a")
            Assert.assertEquals(firstAppointment.profession,"corriente 121")
            Assert.assertEquals(firstAppointment.files,"63ef0ac82a8d7fc1f582dda2")
            Assert.assertEquals(firstAppointment.price,"")
            // MatcherAssert.assertThat(request.headers.get("Content-type"),CoreMatchers.`is`("applocation/json"))

        }
    @Test
    fun `Appointment patient is fetched  correctly`() =
        runBlocking(Dispatchers.Main) {


            val data = service.getPatientturno("639e5b3678539b79cd8dfbe1")
            val patientResponse = newsRepository.getPatientAppointment("639e5b3678539b79cd8dfbe1")

            var request = mockWebServer.takeRequest()
            val result =(data).result


            MatcherAssert.assertThat(request.path,CoreMatchers.`is`("/appointment/patients/639e5b3678539b79cd8dfbe1"))
            MatcherAssert.assertThat(request.method,CoreMatchers.`is`("GET"))

            MatcherAssert.assertThat(result.size,CoreMatchers.`is`(3))
            MatcherAssert.assertThat(patientResponse.size,CoreMatchers.`is`(3))

            val firstAppointment  = result[0]
            Assert.assertEquals(firstAppointment._id,"63ef0b052a8d7fc1f582dda4")
            Assert.assertEquals(firstAppointment.fecha,"18022023")
            Assert.assertEquals(firstAppointment.hora,"09:00")
            Assert.assertEquals(firstAppointment.coment,"")
            Assert.assertEquals(firstAppointment.specialty,"Sangre")
            Assert.assertEquals(firstAppointment.patient,"639e5b3678539b79cd8dfbe1")
            Assert.assertEquals(firstAppointment.medical,"639e34145bb5f9cceeaa8a6a")
            Assert.assertEquals(firstAppointment.profession,"corriente 121")
            Assert.assertEquals(firstAppointment.files,"63ef0ac82a8d7fc1f582dda2")
            Assert.assertEquals(firstAppointment.price,"")
            // MatcherAssert.assertThat(request.headers.get("Content-type"),CoreMatchers.`is`("applocation/json"))

        }

}

class MockAppointmentDao : AppointmentDao {

    private val appointments = MutableLiveData(listOf(appointment1, appointment2, appointment3));
    override fun insert(appointment: Appointment) {
        appointments.value = appointments.value?.toMutableList()?.apply { add(appointment) }

    }

    override fun getAllAppointment(): LiveData<List<Appointment>> = appointments

    override fun deleteAppointment(appointment: Appointment) {
        appointments.value = appointments.value?.toMutableList()?.apply { remove(appointment) }

    }

    override fun deleteAllAppointment() {
        appointments.value = appointments.value?.toMutableList()?.apply { removeAll(appointments.value!!) }

    }
}

val myDispatcher: Dispatcher = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/appointment/medicals/639e34145bb5f9cceeaa8a6a" -> MockResponse().apply { addResponse("api_appointment.json") }
            "/appointment/patients/639e5b3678539b79cd8dfbe1" -> MockResponse().apply { addResponse("api_appointment.json") }
            else -> MockResponse().setResponseCode(404)
        }
    }
}

fun MockResponse.addResponse(filePath: String): MockResponse {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)?.source()?.buffer()

    inputStream?.let {
        setResponseCode(200)
        setBody(it.readString(Charsets.UTF_8))
    }
    return this
}


















