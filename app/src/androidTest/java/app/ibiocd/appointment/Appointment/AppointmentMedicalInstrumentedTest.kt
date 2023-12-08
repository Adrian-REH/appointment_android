package app.ibiocd.appointment.Appointment

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ibiocd.appointment.appointment.view.AppointmentActivity
import app.ibiocd.appointment.appointment.view.AppointmentNavHost
import com.nopalsoft.simple.rest.presentation.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppointmentMedicalInstrumentedTest {

    @get:Rule(order  =1)
    var hiltTestRule=HiltAndroidRule(this)

    @get:Rule(order  =2)
    var composeTestRule= createAndroidComposeRule<AppointmentActivity>()
    lateinit var navController: TestNavHostController

    @Test
    fun AppointmentNavHost_Medical_AddNewItem(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(true,"IDm 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }

        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("MedicalList").performClick()
        composeTestRule.onNodeWithText("Name 0 Last 0").performClick()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").performClick()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").performClick()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("10:00").performClick()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }
    @Test
    fun AppointmentNavHost_Medical_AddNewItem_Medical_Null(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(true,"IDm 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }
        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("FormSee").assertDoesNotExist()
        composeTestRule.onNodeWithText("10:00").assertDoesNotExist()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }

    @Test
    fun AppointmentNavHost_Medical_AddNewItem_Patient_Null(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(true,"IDm 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }
        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("MedicalList").performClick()
        composeTestRule.onNodeWithText("Name 0 Last 0").performClick()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").performClick()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()

        composeTestRule.onNodeWithContentDescription("CalendarSee").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("FormSee").assertDoesNotExist()
        composeTestRule.onNodeWithText("10:00").assertDoesNotExist()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }

    @Test
    fun AppointmentNavHost_Medical_AddNewItem_Specialty_Null(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(true,"IDm 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }
        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("MedicalList").performClick()
        composeTestRule.onNodeWithText("Name 0 Last 0").performClick()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").performClick()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("FormSee").assertDoesNotExist()
        composeTestRule.onNodeWithText("10:00").assertDoesNotExist()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }

    @Test
    fun AppointmentNavHost_Medical_AddNewItem_Null(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(true,"IDm 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }

        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()

    }


    @Test
    fun AppointmentNavHost_Patient_addNewItme(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(false,"IDp 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }

        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("MedicalList").performClick()
        composeTestRule.onNodeWithText("Name 0 Last 0").performClick()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").performClick()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").performClick()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("10:00").performClick()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }
    @Test
    fun AppointmentNavHost_Patient_addNewItme_Medical_Null(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(false,"IDp 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }

        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("MedicalList").performClick()
        composeTestRule.onNodeWithText("Name 0 Last 0").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("FormSee").assertDoesNotExist()
        composeTestRule.onNodeWithText("10:00").assertDoesNotExist()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }
    @Test
    fun AppointmentNavHost_Patient_addNewItme_Patient_Null(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(false,"IDp 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }


        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("MedicalList").performClick()
        composeTestRule.onNodeWithText("Name 0 Last 0").performClick()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").performClick()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").performClick()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("FormSee").assertDoesNotExist()
        composeTestRule.onNodeWithText("10:00").assertDoesNotExist()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }
    @Test
    fun AppointmentNavHost_Patient_addNewItme_Specialty_Null(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(false,"IDp 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }

        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("MedicalList").performClick()
        composeTestRule.onNodeWithText("Name 0 Last 0").performClick()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").performClick()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").assertIsEnabled()
        composeTestRule.onNodeWithContentDescription("FormSee").assertDoesNotExist()
        composeTestRule.onNodeWithText("10:00").assertDoesNotExist()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }
    @Test
    fun AppointmentNavHost_Patient_addNewItme_Null(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(false,"IDp 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }

        composeTestRule.onNodeWithText("MEDICAL").performClick()
        composeTestRule.onNodeWithContentDescription("MedicalList").performClick()
        composeTestRule.onNodeWithText("Name 0 Last 0").performClick()
        composeTestRule.onNodeWithContentDescription("ProfessionList").performClick()
        composeTestRule.onNodeWithText("PROFF").performClick()
        composeTestRule.onNodeWithContentDescription("SpecialtyList").performClick()
        composeTestRule.onNodeWithText("TITLE").performClick()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("10:00").performClick()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()

    }






}