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
class AppointmentLabInstrumentedTest {

    @get:Rule(order  =1)
    var hiltTestRule=HiltAndroidRule(this)

    @get:Rule(order  =2)
    var composeTestRule= createAndroidComposeRule<AppointmentActivity>()
    lateinit var navController: TestNavHostController



    @Test
    fun AppointmentNavHost_Lab_Patient_addNewItme(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(false,"IDp 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }

        composeTestRule.onNodeWithText("LABORATORY").performClick()
        composeTestRule.onNodeWithContentDescription("LaboratoryList").performClick()
        composeTestRule.onNodeWithText("Name 0 Lab 0").performClick()
        composeTestRule.onNodeWithContentDescription("SedeList").performClick()
        composeTestRule.onNodeWithText("Name 0 Sede 0").performClick()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").performClick()
        composeTestRule.onNodeWithContentDescription("AnalisisList").performClick()
        composeTestRule.onNodeWithText("TITLE").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("10:00").performClick()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()
    }
    @Test
    fun AppointmentNavHost_Lab_Medical_addNewItme(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(true,"IDm 0","appointmentID","HomeSelect/?newText=",navController,onDownload={

            })
        }

        composeTestRule.onNodeWithText("LABORATORY").performClick()
        composeTestRule.onNodeWithContentDescription("LaboratoryList").performClick()
        composeTestRule.onNodeWithText("Name 0 Lab 0").performClick()
        composeTestRule.onNodeWithContentDescription("SedeList").performClick()
        composeTestRule.onNodeWithText("Name 0 Sede 0").performClick()
        composeTestRule.onNodeWithContentDescription("PatientList").performClick()
        composeTestRule.onNodeWithText("NAME 0 LAST 0").performClick()
        composeTestRule.onNodeWithContentDescription("AnalisisList").performClick()
        composeTestRule.onNodeWithText("TITLE").performClick()
        composeTestRule.onNodeWithContentDescription("CalendarSee").performClick()
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("10:00").performClick()
        composeTestRule.onNodeWithText("CONFIRMAR").performClick()
    }

    @Test
    fun AppointmentNavHost_Lab_Medical_seeItme(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(true,"IDm 0","IDa 0","AppointmentLab/?newText={newText}",navController,onDownload={

            })
        }

    }

    @Test
    fun AppointmentNavHost_Lab_Patient_seeItme(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(false,"IDp 0","IDa 0","AppointmentLab/?newText={newText}",navController,onDownload={

            })
        }

    }

    @Test
    fun AppointmentNavHost_Lab_Medical_newFils(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppointmentNavHost(false,"IDp 0","IDa 0","AppointmentLab/?newText={newText}",navController,onDownload={

            })
        }

    }





}