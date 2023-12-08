package app.ibiocd.appointment.home

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ibiocd.appointment.auth.view.SessionActivity
import app.ibiocd.appointment.home.navegation.HomeDestinations
import app.ibiocd.appointment.home.navegation.NavigationHome
import app.ibiocd.appointment.home.navegation.NavigationMedicalHost
import app.ibiocd.appointment.profile.model.repository.DateRepository
import app.ibiocd.appointment.profile.model.repository.DateRepositoryImpl
import app.ibiocd.appointment.profile.viewmodel.DateViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeInstrumentedTest {

        @get:Rule(order  =1)
        var hiltTestRule= HiltAndroidRule(this)

        @get:Rule(order  =2)
        var composeTestRule= createAndroidComposeRule<SessionActivity>()
        lateinit var navControllerHome: TestNavHostController
        lateinit var navController: TestNavHostController


    @Test
    fun patient_home_User(){
        composeTestRule.setContent {
             navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            navControllerHome = TestNavHostController(LocalContext.current)
            navControllerHome.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationHome(false,"EMAIL",navControllerHome,navController)
        }

        composeTestRule.onNodeWithTag("greeting").assertTextContains("Hola")

        composeTestRule.onNodeWithTag("card_new_appointment").assertExists()
        composeTestRule.onNodeWithTag("request_new_appointment").assertTextContains("Solicita un turno")

        var result = composeTestRule.onAllNodesWithTag("card_appointment")
        result[0].assertExists()
        result[1].assertExists()



    }

    @Test
    fun medical_home_User(){
        composeTestRule.setContent {


             navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            navControllerHome = TestNavHostController(LocalContext.current)
            navControllerHome.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationHome(true,"EMAIL",navControllerHome,navController)
        }
        runBlocking {
            composeTestRule.awaitIdle()

        }
        composeTestRule.onNodeWithTag("greeting").assertTextContains("Hola")
        composeTestRule.onNodeWithTag("card_new_appointment").assertExists()
        composeTestRule.onNodeWithTag("request_new_appointment").assertTextContains("Solicita un turno")

        var result = composeTestRule.onAllNodesWithTag("card_appointment")
        result[0].assertExists()
        result[1].assertExists()
        runBlocking {
            composeTestRule.awaitIdle()

        }




    }
    @Test
    fun medical_Calendar_User(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            navControllerHome = TestNavHostController(LocalContext.current)
            navControllerHome.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationHome(true,"EMAIL",navControllerHome,navController)
        }
        composeTestRule.runOnUiThread {
            navController.navigate(HomeDestinations.Calendar.route)

        }
        runBlocking {
            composeTestRule.awaitIdle()

        }
        composeTestRule.onNodeWithTag("title_calendar_select").assertTextContains("Selecciona una fecha")

        var result = composeTestRule.onAllNodesWithTag("card_appointment")
        result[0].assertExists()



        runBlocking {
            composeTestRule.awaitIdle()

        }




    }

    @Test
    fun medical_Menu_User(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            navControllerHome = TestNavHostController(LocalContext.current)
            navControllerHome.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationHome(true,"EMAIL",navControllerHome,navController)
        }
        composeTestRule.runOnUiThread {
            navController.navigate(HomeDestinations.Menu.route)

        }
        runBlocking {
            composeTestRule.awaitIdle()

        }

        composeTestRule.onNodeWithTag("hello_menu_profile").assertTextContains("Hola")
        composeTestRule.onNodeWithTag("btn_profile_menu").assertHasClickAction()

        var result = composeTestRule.onAllNodesWithTag("card_appointment")


        runBlocking {
            composeTestRule.awaitIdle()

        }

    }
}