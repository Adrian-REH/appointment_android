package app.ibiocd.appointment.endToEnd

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ibiocd.appointment.auth.view.SessionActivity
import app.ibiocd.appointment.auth.view.SessionNavHost
import app.ibiocd.appointment.home.navegation.HomeDestinations
import app.ibiocd.appointment.home.navegation.NavigationHome
import app.ibiocd.appointment.home.view.HomeActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SignInToProfileInstrumentedTest {

        @get:Rule(order  =1)
        var hiltTestRule= HiltAndroidRule(this)

        @get:Rule(order  =2)
        var composeTestRule= createAndroidComposeRule<SessionActivity>()
        lateinit var navController: TestNavHostController

        //TODO hacer hasta Perfil, cambio de password etc...
    @Test
    fun patient_LogIn(){
        val user="adrianherrera.r.e@gmail.com"
        val pass="dexter"

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SessionNavHost(navController,"Beginning/?newText=","")
        }

        composeTestRule.onNodeWithText("Iniciar sesion").performClick()
        composeTestRule.waitForIdle() // Esperar a que se complete la interacción
        composeTestRule.onNodeWithText("PACIENTE").performClick()
        form(user,pass)

        composeTestRule.waitForIdle() // Esperar a que se complete la interacción


    }


    fun form(user:String, pass:String){
        composeTestRule.onNodeWithTag("user_session").performTextInput(user)
        composeTestRule.waitForIdle() // Esperar a que se complete la interacción
        composeTestRule.onNodeWithTag("user_session").assertTextContains(user)
        composeTestRule.waitForIdle() // Esperar a que se complete la interacción

        composeTestRule.onNodeWithTag("pass_session").performTextInput(pass)
        composeTestRule.waitForIdle() // Esperar a que se complete la interacción
        composeTestRule.onNodeWithContentDescription("Ocultar contraseña").performClick()
        composeTestRule.waitForIdle() // Esperar a que se complete la interacción
        composeTestRule.onNodeWithTag("pass_session").assertTextContains(pass)
        composeTestRule.waitForIdle() // Esperar a que se complete la interacción
        composeTestRule.onNodeWithText("Continuar").performClick()
        composeTestRule.waitForIdle() // Esperar a que se complete la interacción

    }



}